package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeHotDriveInfo

interface HomeHotDriveDataSource {
    fun fetchData() : MutableList<HomeHotDriveInfo>
}