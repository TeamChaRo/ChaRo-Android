package com.example.charo_android.data

import com.example.charo_android.DetailViewpagerImageInfo

interface DetailViewpagerImageDataSource {
    fun fetchData(): MutableList<DetailViewpagerImageInfo>
}