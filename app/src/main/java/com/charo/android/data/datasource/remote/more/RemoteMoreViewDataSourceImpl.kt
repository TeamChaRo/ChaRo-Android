package com.charo.android.data.datasource.remote.more

import com.charo.android.data.api.more.MoreViewService
import com.charo.android.data.model.response.more.ResponseMoreViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class RemoteMoreViewDataSourceImpl(private val service: MoreViewService) :
    RemoteMoreViewDataSource {

    override fun getMoreView(
        userEmail: String,
        identifer: String,
        value: String
    ): Flow<ResponseMoreViewData>{
        return flow{
            emit(service.getMoreView(userEmail, identifer, value))
        }
    }

}