package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeTodayDriverInfo

interface HomeTodayDriveDataSource {
    fun fetchData() : MutableList<HomeTodayDriverInfo>
}