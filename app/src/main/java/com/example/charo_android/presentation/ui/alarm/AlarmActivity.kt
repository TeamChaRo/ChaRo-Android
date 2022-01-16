package com.example.charo_android.presentation.ui.alarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.databinding.ActivityAlarmBinding
import com.example.charo_android.presentation.ui.main.MainActivity

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel

    private val alarmAdapter = AlarmListAdapter() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goHome()

        initRecyclerView()

        alarmViewModel = AlarmViewModel()

    }

    private fun initRecyclerView() {
        with(binding) {
            rcvAlarmList.adapter = alarmAdapter
            Log.e("alarmAdapter", alarmAdapter.itemList.toString())
            alarmAdapter.notifyDataSetChanged()
        }
    }

    private fun goHome() {
        binding.imgBackHomeAlarm.setOnClickListener {
            onBackPressed()
        }
    }
}
