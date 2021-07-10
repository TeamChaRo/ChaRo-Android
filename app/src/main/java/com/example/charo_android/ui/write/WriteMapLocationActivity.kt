package com.example.charo_android.ui.write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.example.charo_android.databinding.ActivityWriteMapLocationBinding
import com.skt.Tmap.TMapView

class WriteMapLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgWriteMapLocationBack.setOnClickListener {
            onBackPressed()

        }


        val tMapView = TMapView(this@WriteMapLocationActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("")
        binding.clWriteMapLocationView.addView(tMapView)
    }
}