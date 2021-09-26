package com.example.charo_android.data.repository.remote.more

import com.example.charo_android.data.api.more.MoreNewViewService
import com.example.charo_android.data.model.response.more.ResponseMoreViewData

class RemoteMoreNewViewDataSourceImpl(private val service: MoreNewViewService) : RemoteMoreNewViewDataSource {
    override suspend fun getMoreNewView(
        userEmail: String,
        identifer: String,
        value: String
    ): ResponseMoreViewData = service.getMoreNewView(userEmail, identifer, value)
}