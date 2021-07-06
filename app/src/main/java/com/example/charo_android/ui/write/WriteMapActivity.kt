package com.example.charo_android.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityWriteMapBinding

class WriteMapActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWriteMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}