package com.charo.android.presentation.ui.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.databinding.ActivitySocialSignInBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.signin.viewmodel.SocialSignInViewModel
import com.charo.android.presentation.ui.signup.SignUpActivity
import com.charo.android.presentation.util.SharedInformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SocialSignInActivity() :
    BaseActivity<ActivitySocialSignInBinding>(R.layout.activity_social_sign_in) {
    private val socialSignInViewModel: SocialSignInViewModel by viewModel()
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //deeplink로 왔을 때 로그인 화면 건너뛰고 메인 -> 게시물
        val postId = intent.getIntExtra("postId", -1)
        if (intent != null && postId != -1) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("postId", postId)
            startActivity(intent)
        }
        checkKakaoSignUp()
        autoLogin()
        initKakaoLogin()
        initGoogleLogin()
        googleSignIn()
        clickGoogleSignIn()
        lookForMain()
        goEmailLogin()
        goEmailSignUp()
        goKaKaoMain()
    }

    //자동 로그인
    private fun autoLogin() {
        val autoEmail = SharedInformation.getEmail(this)
        Timber.d("autoEmail $autoEmail")
        if (autoEmail != "@") {
            Timber.d("autoEmail $autoEmail")
            Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //kakao
    private fun initKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Timber.e("kakao 로그인 실패 $error")
            } else if (token != null) {
                Timber.i("kakao 로그인 성공 ${token.accessToken}")
                kakaoUserEmail()
            }
        }
        binding.imgSocialKakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    Timber.d("kakao 토큰 정보 보기 실패")
                } else {
                    kakaoUserEmail()

                }
            }
        }

    }

    //카카오
    fun kakaoUserEmail() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Timber.e("kakao 사용자 정보 요청 실패 $error")
            } else if (user != null) {
                if (user.kakaoAccount?.email != null) {
                    Timber.i(
                        "kakaoUser 사용자 정보 요청 성공 \n이메일: ${user.kakaoAccount?.email}"
                    )
                    socialSignInViewModel.kakaoLoginSuccess(
                        RequestSocialData(
                            user.kakaoAccount?.email ?: ""
                        )
                    )
                } else {
                    Toast.makeText(this, "카카오 로그인시 이메일 동의 해주세요.", Toast.LENGTH_SHORT).show()
                    UserApiClient.instance.unlink { }
                }
            }
        }
    }

    //카카오 로그인 성공시 main 이동
    private fun goKaKaoMain() {
        if (SharedInformation.getLogout(this) != "Logout") {
            socialSignInViewModel.kakaoSuccess.observe(this, Observer {
                if (socialSignInViewModel.socialStatus.value != 404) {
                    SharedInformation.setLogout(this, "LogIn")
                    SharedInformation.setEmail(this, it?.email.toString())
                    Toast.makeText(this, "카카오 로그인 성공", Toast.LENGTH_SHORT).show()
                    SharedInformation.setNickName(this, it?.nickname.toString())
                    SharedInformation.saveSocialId(this@SocialSignInActivity, "1")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    //카카오 로그인 실패시(회원가입 이슈)
    private fun checkKakaoSignUp() {
        socialSignInViewModel.socialStatus.observe(this) {
            if (it == 404) {
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
                SharedInformation.setSignUp(this, 2)
                val intent = Intent(this, SignUpActivity::class.java)
                intent.putExtra("kakaoSignUpEmail", SharedInformation.getEmail(this))
                startActivity(intent)
                finish()
            }
        }
    }


    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //구글 로그인 관리 클래스
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
    }

    private fun googleSignIn() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Timber.d("구글 ${account.id.toString()}")
                        firebaseAuthWithGoogle(account.idToken)
                    } catch (e: ApiException) {
                        Timber.d("구글 Google sign in failed")
                        Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Timber.d("구글 ${result.resultCode.toString() + "+" + RESULT_OK.toString()}")
                    Timber.e("구글 진짜 제발 야!")
                    Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun clickGoogleSignIn() {
        binding.imgSocialGoogle.setOnClickListener {
            SharedInformation.setLogout(this, "LogIn")
            val intent = googleSignInClient?.signInIntent
            resultLauncher.launch(intent)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val email = user?.email
                    val profileImage = user?.photoUrl
                    Timber.d("구글 ${email.toString()}")
                    // 첫 회원가입 vs 로그인
                    socialSignInViewModel.googleLoginSuccess(RequestSocialData(email ?: ""))
                    checkGoogleLoginError(profileImage, email)
                    socialSignInViewModel.googleSuccess.observe(this) {
                        if (SharedInformation.getLogout(this) != "Logout") {
                            Toast.makeText(
                                this@SocialSignInActivity, "구글 로그인 성공",
                                Toast.LENGTH_SHORT
                            ).show()
                            SharedInformation.setNickName(this, it?.nickname.toString())
                            SharedInformation.setEmail(this, email.toString())
                            SharedInformation.saveSocialId(this, "2")
                            val intent =
                                Intent(this@SocialSignInActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
    }


    //구글 로그인 분기처리(회원이 없을 경우 또는 있을 경우)
    private fun checkGoogleLoginError(profileImage: Uri?, email: String?) {
        socialSignInViewModel.googleSocialStatus.observe(this) {
            if (it == 404) {
                SharedInformation.setSignUp(this, 1)
                Toast.makeText(
                    this@SocialSignInActivity, "약관 동의가 필요합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent =
                    Intent(this@SocialSignInActivity, SignUpActivity::class.java)
                intent.apply {
                    putExtra("googleProfileImage", profileImage.toString())
                    putExtra("googleSignUpEmail", email)
                    putExtra("googleSignUp", 1)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    //둘러보기
    private fun lookForMain() {
        binding.textSocialLook.setOnClickListener {
            SharedInformation.setEmail(this, "@")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goEmailLogin() {
        binding.textSocialEmailLogin.setOnClickListener {
            SharedInformation.setSignUp(this, 0)
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goEmailSignUp() {
        binding.textSocialEmailSignUp.setOnClickListener {
            SharedInformation.setSignUp(this, 0)
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}