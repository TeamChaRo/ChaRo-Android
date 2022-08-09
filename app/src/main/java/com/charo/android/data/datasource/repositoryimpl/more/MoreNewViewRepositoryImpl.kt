package com.charo.android.data.datasource.repositoryimpl.more

import com.charo.android.data.datasource.remote.more.RemoteMoreNewViewDataSource
import com.charo.android.data.model.response.more.ResponseMoreViewData
import com.charo.android.domain.repository.moreview.MoreNewViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MoreNewViewRepositoryImpl(private val dataSource: RemoteMoreNewViewDataSource) :
    MoreNewViewRepository {
    override fun getMoreNewView(
        userEmail: String,
        identifer: String,
        value: String
    ): Flow<ResponseMoreViewData> = flow {
        dataSource.getMoreNewView(userEmail, identifer, value).collect{
            emit(it)
        }
    }

}