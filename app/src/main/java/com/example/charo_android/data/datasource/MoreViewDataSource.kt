package com.example.charo_android.data.datasource

import com.example.charo_android.data.response.ResponseMoreViewData

interface MoreViewDataSource {
    fun fetchData() : MutableList<ResponseMoreViewData>
}