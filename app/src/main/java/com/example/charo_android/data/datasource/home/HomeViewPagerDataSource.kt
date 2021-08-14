package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeViewPagerInfo

interface HomeViewPagerDataSource {
    fun fetchData() : MutableList<HomeViewPagerInfo>
}