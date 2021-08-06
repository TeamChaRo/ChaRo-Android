package com.example.charo_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityDetailBinding.inflate
import com.example.charo_android.databinding.ActivitySplashBinding
import com.example.charo_android.ui.onBoarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private val time : Long = 3000

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

       Handler().postDelayed({
           startActivity(Intent(this,OnBoardingActivity::class.java))
           finish()
       }, time)



    }




}