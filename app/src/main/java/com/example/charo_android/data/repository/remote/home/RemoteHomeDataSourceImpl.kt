package com.example.charo_android.data.repository.remote.home

import com.example.charo_android.data.api.HomeViewService
import com.example.charo_android.data.model.response.ResponseHomeViewData

class RemoteHomeDataSourceImpl(private val service : HomeViewService) : RemoteHomeDataSource {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData = service.getMain(userEmail)



}