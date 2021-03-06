package com.charo.android.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivitySplashBinding
import com.charo.android.presentation.ui.onboarding.OnBoardingActivity
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    private val time: Long = 2000
    private var deepLinkPostId: Int = -1
    private var mContext: Context = this

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            val autoEmail = SharedInformation.getEmail(mContext)
            val onBoardingChecked = SharedInformation.getOnBoarding(mContext)
            val socialKey = SharedInformation.getSocialId(mContext)

            Timber.d("autoEmail $autoEmail")
            Timber.d("socialKey $socialKey")
            Timber.d("onBoardingChecked $onBoardingChecked")

            handleDeepLink()
            delay(time)
            autoLogin(autoEmail, socialKey, onBoardingChecked)
        }
    }

    private fun autoLogin(autoEmail: String, socialKey: String, onBoardingChecked: Boolean) {
        Timber.d("autoEmail $autoEmail")
        Timber.d("[DynamicLink] autoLogin $deepLinkPostId")

        if (autoEmail == "@") {
            val intent: Intent = if (onBoardingChecked) {
                Intent(this, OnBoardingActivity::class.java)
            } else {
                Intent(this, SocialSignInActivity::class.java)
            }
            runActivity(intent)
        } else {
            autoLogin(autoEmail, socialKey)
        }
    }

    private fun autoLogin(autoEmail: String, socialKey: String) {
        if (autoEmail == "@") {
            return
        }

        //????????? ?????????
        if(socialKey == "1"){
            //?????? ??????
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Timber.e("kakao ?????? ????????? ?????? $error")
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            //?????? ??????
                            Toast.makeText(this, "????????? ?????????????????????. ?????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                            Timber.e("kakao ?????? ?????? $error")
                        } else {
                            //?????? ??????
                            Timber.e("kakao ?????? ?????? $error")
                        }

                        removeAutoInfo()

                        val intent = Intent(this, SocialSignInActivity::class.java)
                        runActivity(intent)

                    } else {
                        //????????? ?????? , ?????? ?????????
                        Timber.e("kakao ?????? ????????? ?????? $tokenInfo")
                        Toast.makeText(this, "?????? ????????? ??????", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        runActivity(intent)
                    }
                }
            } else {
                //?????? ?????? , ????????? ??????
                Timber.e("kakao ?????? ??????")

                val intent = Intent(this, SocialSignInActivity::class.java)
                runActivity(intent)

                removeAutoInfo()
                return
            }
        } else { //??????, ????????? ?????????
            Timber.e("?????? ????????? ??????")
            Toast.makeText(this, "?????? ????????? ??????", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            runActivity(intent)
        }
    }

    private fun removeAutoInfo(){
        SharedInformation.setLogout(this, "Logout")
        SharedInformation.removeNickName(this)
        SharedInformation.removeEmail(this)
        SharedInformation.removeSocialId(this)
    }

    private fun runActivity(intent : Intent){
        Timber.d("[DynamicLink] autoLogin $deepLinkPostId")
        intent.putExtra("postId", deepLinkPostId)
        startActivity(intent)
        finish()
    }

    private suspend fun handleDeepLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    Timber.d("[DynamicLink] deepLink $deepLink")

                    if (deepLink != null) {
                        val segment = deepLink.lastPathSegment
                        Timber.d("[DynamicLink] segment $segment")

                        if (segment == Define().DTL_SEGMENT) {
                            val strPostId = deepLink.getQueryParameter("postId")
                            deepLinkPostId = strPostId?.toInt() ?: -1
                            Timber.d("[DynamicLink] deepLinkPostId $deepLinkPostId")
                        }
                    }
                }
            }
            .addOnFailureListener(this) { e ->
                run {
                    Timber.w("[DynamicLink] getDynamicLink:onFailure $e")
                }
            }
    }
}