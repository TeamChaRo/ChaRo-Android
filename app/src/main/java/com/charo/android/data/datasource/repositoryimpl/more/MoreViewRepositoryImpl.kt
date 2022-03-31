package com.charo.android.data.datasource.repositoryimpl.more

import com.charo.android.data.datasource.remote.more.RemoteMoreViewDataSource
import com.charo.android.data.model.response.more.ResponseMoreViewData
import com.charo.android.domain.repository.moreview.MoreViewRepository


class MoreViewRepositoryImpl(private val remoteDataSource: RemoteMoreViewDataSource) :
    MoreViewRepository {
    override suspend fun getMoreView(
        userEmail: String,
        identifer: String,
        value: String
    ): ResponseMoreViewData {
        return remoteDataSource.getMoreView(userEmail, identifer, value)
    }
}