package com.example.charo_android.data.repository.remote.more

import com.example.charo_android.data.api.more.MoreViewService
import com.example.charo_android.data.model.response.more.ResponseMoreViewData

class RemoteMoreViewDataSourceImpl(private val service:MoreViewService) : RemoteMoreViewDataSource {
    override suspend fun getMoreView(userEmail: String, identifer: String, value: String) :ResponseMoreViewData
    = service.getMoreView(userEmail, identifer, value)
}