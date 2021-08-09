package com.example.charo_android.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityAlarmBinding
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.hidden.Hidden.Companion.userId

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goHome()


    }

    private fun goHome() {
        binding.imgBackHomeAlarm.setOnClickListener {
            onBackPressed()
        }
    }
}
