package com.example.charo_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

//        initDetailImageItem()
//        viewpagerDetailImageOnClickEvent()
//        checkImgDetailViewpagerPrevNext()
//        imgDetailViewpagerPrevOnClickEvent()
    }

//    private fun initDetailImageItem() {
//        binding.viewpagerDetailImage.adapter = detailViewPagerAdapter
//        detailViewPagerAdapter.imageList.addAll(LocalDetailViewpagerImageDataSource().fetchData())
//        detailViewPagerAdapter.notifyDataSetChanged()
//    }
//
//    private fun viewpagerDetailImageOnClickEvent() {
//        binding.viewpagerDetailImage.setOnClickListener() {
//
//        }
//    }
//
//    private fun checkImgDetailViewpagerPrevNext() {
//        val viewpagerDetailImage = binding.viewpagerDetailImage
//        val currentPageNumber = viewpagerDetailImage.currentItem
//        Log.d("curPageNum", currentPageNumber.toString())
//
//        if(currentPageNumber == 0){
//            binding.imgDetailViewpagerPrev.visibility = View.GONE
//        } else if(currentPageNumber == detailViewPagerAdapter.itemCount - 1){
//            binding.imgDetailViewpagerNext.visibility = View.GONE
//        } else{
//            binding.imgDetailViewpagerNext.visibility = View.VISIBLE
//            binding.imgDetailViewpagerPrev.visibility = View.VISIBLE
//        }
//    }
//
//    private fun imgDetailViewpagerPrevOnClickEvent() {
//        binding.imgDetailViewpagerPrev.setOnClickListener() {
//
//        }
//    }
}
