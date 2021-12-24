package com.example.charo_android.presentation.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.example.charo_android.R
import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.databinding.ActivitySocialSignInBinding
import com.example.charo_android.presentation.base.BaseActivity
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.signin.viewmodel.SocialSignInViewModel
import com.example.charo_android.presentation.ui.signup.SignUpActivity
import com.example.charo_android.presentation.util.SharedInformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_social_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SocialSignInActivity() :
    BaseActivity<ActivitySocialSignInBinding>(R.layout.activity_social_sign_in) {
    private val socialSignInViewModel: SocialSignInViewModel by viewModel()
    private var googleSignInClient: GoogleSignInClient? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLogin()
        initGoogleLogin()
        clickGoogleSignIn()
        googleSignIn()
        lookForMain()
        goEmailLogin()
        goEmailSignUp()
        autoLogin()
        goKaKaoMain()
    }
    //자동 로그인
    private fun autoLogin(){
        val autoEmail = SharedInformation.getEmail(this)
        Log.d("autoEmail", autoEmail)
        if (autoEmail != ""){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //kakao
    private fun initKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao", "로그인 실패", error)
            } else if (token != null) {
                Log.i("kakao", "로그인 성공 ${token.accessToken}")
                Toast.makeText(this, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                kakaoUserEmail()
                SharedInformation.saveSocialId(this, "1")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    Log.d("kakao", "토큰 정보 보기 실패")
                } else {
                    kakaoUserEmail()
                }
            }


        } else {
            binding.imgSocialKakao.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)

                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }
        }
    }


    private fun kakaoUserEmail() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakao", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                if (user?.kakaoAccount?.email == null) {
                    Toast.makeText(this, "이메일이 없어 회원가입이 필요합니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    SharedInformation.setEmail(this, user.kakaoAccount?.email!!)
                    Log.i(
                        "kakao", "사용자 정보 요청 성공" +
                                "\n이메일: ${user.kakaoAccount?.email}"
                    )
                    socialSignInViewModel.kakaoLoginSuccess(
                        RequestSocialData(
                            user.kakaoAccount?.email ?: ""
                        )
                    )
                }
            }
        }
    }
    //카카오 로그인 성공시 main 이동
    private fun goKaKaoMain(){
        socialSignInViewModel.success.observe(this, Observer {
            if(it){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


    }


    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()

        //구글 로그인 관리 클래스
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun googleSignIn() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Log.d("google", account.id)
                        firebaseAuthWithGoogle(account.idToken)
                    } catch (e: ApiException) {
                        Log.w("google", "Google sign in failed", e)
                    }
                }
            }
    }

    private fun clickGoogleSignIn() {
        binding.imgSocialGoogle.setOnClickListener {
            val intent = googleSignInClient?.signInIntent
            resultLauncher.launch(intent)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val email = user?.email
                    Log.d("googles", email.toString())
                    socialSignInViewModel.googleLoginSuccess(RequestSocialData(email ?: ""))
                    socialSignInViewModel.success.observe(this, Observer {
                        if (it == false) {
                            Toast.makeText(
                                this@SocialSignInActivity, "이메일이 없어 회원가입이 필요합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(this@SocialSignInActivity, SignUpActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@SocialSignInActivity, "로그인에 성공하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            SharedInformation.saveSocialId(this, "2")
                            val intent = Intent(this@SocialSignInActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })


                } else {
                    Toast.makeText(this, "잠시후 다시 시작하세요", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun lookForMain() {
        binding.textSocialLook.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goEmailLogin(){
        binding.textSocialEmailLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goEmailSignUp(){
        binding.textSocialEmailSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}