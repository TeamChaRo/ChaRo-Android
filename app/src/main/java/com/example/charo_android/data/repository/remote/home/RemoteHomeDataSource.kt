package com.example.charo_android.data.repository.remote.home

import com.example.charo_android.data.model.response.ResponseHomeViewData

interface RemoteHomeDataSource {
    suspend fun getMain(userEmail: String): ResponseHomeViewData
}