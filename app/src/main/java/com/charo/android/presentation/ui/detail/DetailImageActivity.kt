package com.charo.android.presentation.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.databinding.ActivityDetailImageBinding

class DetailImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailImageBinding
    private val viewPagerAdapter = DetailImageViewPagerAdapter()
    private lateinit var imageList: ArrayList<String>
    private var itemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageList = intent.getSerializableExtra("imageList") as ArrayList<String>
        itemPosition = intent.getIntExtra("itemPosition", 0)
        initViewPager()
    }

    private fun initViewPager() {
        // viewPager registerOnPageChangeCallback
        binding.viewPagerDetailImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("Selected_Page", position.toString())
                binding.tvDetailImage.text = "${position+1}/${viewPagerAdapter.itemList.size}"
            }
        })

        binding.apply {
            viewPagerDetailImage.adapter = viewPagerAdapter
            viewPagerAdapter.itemList.addAll(imageList)
            viewPagerAdapter.notifyDataSetChanged()
            tvDetailImage.text = "1/${viewPagerAdapter.itemList.size}"
            viewPagerDetailImage.setCurrentItem(itemPosition, false)
        }
    }
}