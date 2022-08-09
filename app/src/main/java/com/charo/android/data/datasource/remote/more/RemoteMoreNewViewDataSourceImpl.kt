package com.charo.android.data.datasource.remote.more

import com.charo.android.data.api.more.MoreNewViewService
import com.charo.android.data.model.response.more.ResponseMoreViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RemoteMoreNewViewDataSourceImpl(private val service: MoreNewViewService) : RemoteMoreNewViewDataSource {
    override fun getMoreNewView(
        userEmail: String,
        identifer: String,
        value: String
    ): Flow<ResponseMoreViewData> = flow {
        emit(service.getMoreNewView(userEmail, identifer, value))
    }
}