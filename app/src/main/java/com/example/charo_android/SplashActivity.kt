package com.example.charo_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.example.charo_android.databinding.ActivityDetailBinding.inflate
import com.example.charo_android.databinding.ActivityMainBinding.inflate
import com.example.charo_android.databinding.ActivityOnBoardingBinding
import com.example.charo_android.databinding.ActivitySplashBinding
import com.example.charo_android.ui.onBoarding.OnBoardingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

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