package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeViewPagerInfo

interface HomeViewPagerDataSource {
    fun fetchData() : MutableList<HomeViewPagerInfo>
}