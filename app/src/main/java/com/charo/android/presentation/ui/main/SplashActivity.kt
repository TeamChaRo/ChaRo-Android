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

            Firebase.dynamicLinks
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        Timber.d("[DynamicLink] deepLink $deepLink")

                        if(deepLink != null){
                            val segment = deepLink.lastPathSegment
                            Timber.d("[DynamicLink] segment $segment")

                            if(segment == Define().DYNAMIC_SEGMENT){
                                val postId = deepLink.getQueryParameter("postId")
                                val intent = Intent(this, WriteShareActivity::class.java)
                                intent.putExtra("postId", postId)
                                intent.putExtra("destination", "detail")
                                startActivity(intent)
                            }
                        }
                    } else {
                        autoLogin(autoEmail, onBoardingChecked)
                    }
                }
                .addOnFailureListener(this) { e ->
                    run {
                        Timber.w("[DynamicLink] getDynamicLink:onFailure $e")
                        autoLogin(autoEmail, onBoardingChecked)
                    }
                }
        }, time)
    }

    private fun autoLogin(autoEmail: String, onBoardingChecked: Boolean){
        if (autoEmail != "@") {
            Timber.d("autoEmail $autoEmail")
            Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if(onBoardingChecked) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, SocialSignInActivity::class.java))
                finish()
            }
        }
    }
}