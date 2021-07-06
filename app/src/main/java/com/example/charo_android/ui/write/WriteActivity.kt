package com.example.charo_android.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)









    }
}