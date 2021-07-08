package com.example.charo_android.data

import com.example.charo_android.ui.home.HomeLocationDriveInfo

interface HomeLocationDriveDataSource {
    fun fetchData():MutableList<HomeLocationDriveInfo>
}