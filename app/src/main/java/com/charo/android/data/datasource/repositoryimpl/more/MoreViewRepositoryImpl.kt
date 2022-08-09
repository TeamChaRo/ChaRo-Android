package com.charo.android.data.datasource.repositoryimpl.more

import com.charo.android.data.datasource.remote.more.RemoteMoreViewDataSource
import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.data.model.response.more.ResponseMoreViewData
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.repository.moreview.MoreViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class MoreViewRepositoryImpl(private val remoteDataSource: RemoteMoreViewDataSource) :
    MoreViewRepository {
    override fun getMoreView(
        userEmail: String,
        identifer: String,
        value: String
    ): Flow<ResponseMoreViewData> {
        return flow {
            remoteDataSource.getMoreView(userEmail, identifer, value).collect{
                emit(it)
            }
        }
    }
}