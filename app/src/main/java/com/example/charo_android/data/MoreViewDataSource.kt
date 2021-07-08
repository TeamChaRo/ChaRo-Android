package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeViewPagerInfo
import com.example.charo_android.ui.home.more.MoreViewInfo

interface MoreViewDataSource {
    fun fetchData() : MutableList<MoreViewInfo>
}