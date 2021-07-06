package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeThemeInfo
import com.example.charo_android.ui.home.HomeTodayDriverInfo

interface HomeThemeDataSource {
    fun fetchData() : MutableList<HomeThemeInfo>
}