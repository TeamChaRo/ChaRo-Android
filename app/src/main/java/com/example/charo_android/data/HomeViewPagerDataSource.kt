package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeViewPagerImage

interface HomeViewPagerDataSource {
    fun fetchData() : MutableList<HomeViewPagerImage>
}