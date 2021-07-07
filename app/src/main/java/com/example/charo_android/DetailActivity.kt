package com.example.charo_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityDetailViewBinding
import com.skt.Tmap.TMapView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailViewBinding
    private var detailViewPagerAdapter = DetailViewpagerImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tMapView 생성부분
        val tMapView = TMapView(this)
        val tMapViewContainer = binding.clDetailMapview
        tMapView.setSKTMapApiKey("")
        tMapViewContainer.addView(tMapView)

        initDetailImageItem()
    }

    private fun initDetailImageItem() {
        binding.viewpagerDetailImage.adapter = detailViewPagerAdapter
        detailViewPagerAdapter.imageList.addAll(LocalDetailViewpagerImageDataSource().fetchData())
        detailViewPagerAdapter.notifyDataSetChanged()
    }
}