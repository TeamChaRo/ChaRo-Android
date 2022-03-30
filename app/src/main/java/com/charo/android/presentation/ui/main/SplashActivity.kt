package com.charo.android.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivitySplashBinding
import com.charo.android.presentation.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private val time: Long = 2000

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, time)


    }


}