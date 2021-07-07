package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeNightDriveInfo

interface HomeNightDriveDataSource {
    fun fetchData() : MutableList<HomeNightDriveInfo>
}