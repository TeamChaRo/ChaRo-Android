package com.example.charo_android.presentation.ui.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityAlarmBinding

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
