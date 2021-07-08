package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeTodayDriverInfo
import com.example.charo_android.ui.home.HomeViewPagerInfo

interface HomeTodayDriveDataSource {
    fun fetchData() : MutableList<HomeTodayDriverInfo>
}