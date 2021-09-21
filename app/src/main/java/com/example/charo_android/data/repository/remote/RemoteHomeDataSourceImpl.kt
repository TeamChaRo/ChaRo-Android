package com.example.charo_android.data.repository.remote

import android.util.Log
import com.example.charo_android.data.api.HomeViewService
import com.example.charo_android.data.model.response.ResponseHomeViewData
import retrofit2.Response

class RemoteHomeDataSourceImpl(private val service : HomeViewService) : RemoteHomeDataSource {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData = service.getMain(userEmail)



}