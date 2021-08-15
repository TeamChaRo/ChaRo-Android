package com.example.charo_android.data.datasource.home

import com.example.charo_android.data.response.ResponseHomeViewData

interface HomeDataSource {
    suspend fun getMain(userEmail: String): ResponseHomeViewData
}