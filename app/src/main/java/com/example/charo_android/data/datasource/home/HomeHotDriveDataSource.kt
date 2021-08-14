package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeHotDriveInfo

interface HomeHotDriveDataSource {
    fun fetchData() : MutableList<HomeHotDriveInfo>
}