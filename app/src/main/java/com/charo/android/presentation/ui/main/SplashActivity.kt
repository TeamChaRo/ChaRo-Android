package com.charo.android.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivitySplashBinding
import com.charo.android.presentation.ui.onboarding.OnBoardingActivity
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.SharedInformation
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
        }, time)
    }
}