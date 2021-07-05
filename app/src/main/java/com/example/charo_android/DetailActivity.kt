package com.example.charo_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityDetailViewBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}