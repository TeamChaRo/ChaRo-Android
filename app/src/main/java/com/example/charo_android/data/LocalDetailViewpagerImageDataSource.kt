package com.example.charo_android.data

import android.util.Log
import com.example.charo_android.DetailViewpagerImageInfo
import com.example.charo_android.R

class LocalDetailViewpagerImageDataSource: DetailViewpagerImageDataSource {
    override fun fetchData(): MutableList<DetailViewpagerImageInfo> {
        return mutableListOf(
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
            DetailViewpagerImageInfo(image = R.drawable.mask_group_reverse),
            DetailViewpagerImageInfo(image = R.drawable.mask_group),
        )
    }
}