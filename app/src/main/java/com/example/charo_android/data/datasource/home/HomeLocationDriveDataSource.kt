package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeLocationDriveInfo

interface HomeLocationDriveDataSource {
    fun fetchData():MutableList<HomeLocationDriveInfo>
}