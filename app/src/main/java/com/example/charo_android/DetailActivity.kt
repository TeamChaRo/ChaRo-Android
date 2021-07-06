package com.example.charo_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityDetailViewBinding
import com.skt.Tmap.TMapView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tMapView = TMapView(this)
        val tMapViewContainer = binding.clDetailMapview
        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
        tMapViewContainer.addView(tMapView)
    }
}