package com.charo.android.data.datasource.remote.home

import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.home.ResponseHomeViewData

interface RemoteHomeDataSource {
    suspend fun getMain(userEmail: String): ResponseHomeViewData

    suspend fun postLike(requestHomeLikeData: RequestHomeLikeData) : ResponseStatusCode
}