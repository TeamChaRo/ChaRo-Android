package com.charo.android.data.datasource.remote.more

import com.charo.android.data.api.more.MoreViewService
import com.charo.android.data.model.response.more.ResponseMoreViewData


class RemoteMoreViewDataSourceImpl(private val service: MoreViewService) : RemoteMoreViewDataSource {
    override suspend fun getMoreView(userEmail: String, identifer: String, value: String) : ResponseMoreViewData
    = service.getMoreView(userEmail, identifer, value)
}