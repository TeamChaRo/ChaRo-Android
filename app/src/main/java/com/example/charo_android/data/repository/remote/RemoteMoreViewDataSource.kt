package com.example.charo_android.data.repository.remote

import com.example.charo_android.data.model.response.ResponseMoreViewData

interface RemoteMoreViewDataSource {
    fun fetchData() : MutableList<ResponseMoreViewData>
}