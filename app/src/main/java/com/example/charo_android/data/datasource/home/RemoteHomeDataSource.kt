package com.example.charo_android.data.datasource.home

import com.example.charo_android.api.HomeViewService
import com.example.charo_android.data.response.ResponseHomeViewData

class RemoteHomeDataSource(private val service : HomeViewService) : HomeDataSource  {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData = service.getMain(userEmail)
}