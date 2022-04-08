package com.charo.android.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charo.android.R
import com.charo.android.databinding.ActivityBannerSpringPlaylistBinding

class BannerSpringPlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBannerSpringPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerSpringPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}