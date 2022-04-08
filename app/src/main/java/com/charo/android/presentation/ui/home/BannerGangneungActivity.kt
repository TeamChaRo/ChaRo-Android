package com.charo.android.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charo.android.databinding.ActivityBannerGangneungBinding

class BannerGangneungActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBannerGangneungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerGangneungBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}