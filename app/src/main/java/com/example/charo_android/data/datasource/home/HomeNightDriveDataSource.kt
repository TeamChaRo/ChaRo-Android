package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeNightDriveInfo

interface HomeNightDriveDataSource {
    fun fetchData() : MutableList<HomeNightDriveInfo>
}