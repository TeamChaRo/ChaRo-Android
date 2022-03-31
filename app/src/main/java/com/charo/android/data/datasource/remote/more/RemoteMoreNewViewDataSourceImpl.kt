package com.charo.android.data.datasource.remote.more

import com.charo.android.data.api.more.MoreNewViewService
import com.charo.android.data.model.response.more.ResponseMoreViewData


class RemoteMoreNewViewDataSourceImpl(private val service: MoreNewViewService) : RemoteMoreNewViewDataSource {
    override suspend fun getMoreNewView(
        userEmail: String,
        identifer: String,
        value: String
    ): ResponseMoreViewData = service.getMoreNewView(userEmail, identifer, value)
}