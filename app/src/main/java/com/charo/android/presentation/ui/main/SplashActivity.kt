package com.charo.android.presentation.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivitySplashBinding
import com.charo.android.presentation.ui.onboarding.OnBoardingActivity
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    private val time: Long = 2000
    private var deepLinkPostId: String? = null

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val autoEmail = SharedInformation.getEmail(this)
            val onBoardingChecked = SharedInformation.getOnBoarding(this)
            Timber.d("autoEmail $autoEmail")
            Timber.d("onBoardingChecked $onBoardingChecked")

            autoLogin(autoEmail, onBoardingChecked)
            handleDeepLink()
        }, time)
    }

    private fun autoLogin(autoEmail: String, onBoardingChecked: Boolean){
        val intent : Intent = if (autoEmail != "@") {
            Timber.d("autoEmail $autoEmail")
            Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_SHORT).show()
            Intent(this, MainActivity::class.java)
        } else {
            if(onBoardingChecked) {
                Intent(this, OnBoardingActivity::class.java)
            } else {
                Intent(this, SocialSignInActivity::class.java)
            }
        }
        intent.putExtra("postId", deepLinkPostId)
        startActivity(intent)
        finish()
    }

    private fun handleDeepLink(){
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

                        if (segment == Define().DYNAMIC_SEGMENT) {
                            deepLinkPostId = deepLink.getQueryParameter("postId")
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