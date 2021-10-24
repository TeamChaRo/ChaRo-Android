package com.example.charo_android.presentation.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.charo_android.R
import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.databinding.ActivitySocialSignInBinding
import com.example.charo_android.presentation.base.BaseActivity
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.signin.viewmodel.SocialSignInViewModel
import com.example.charo_android.presentation.ui.signup.SignUpActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_social_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SocialSignInActivity() :
    BaseActivity<ActivitySocialSignInBinding>(R.layout.activity_social_sign_in) {
    private val socialSignInViewModel: SocialSignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoLogin()

    }


    private fun initKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakao", "로그인 실패", error)
            } else if (token != null) {
                Log.i("kakao", "로그인 성공 ${token.accessToken}")
                Toast.makeText(this, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                kakaoUserEmail()
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
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
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
                } else {
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
}