package com.charo.android.data.datasource.repositoryimpl.more

import com.charo.android.data.datasource.remote.more.RemoteMoreNewViewDataSource
import com.charo.android.data.model.response.more.ResponseMoreViewData
import com.charo.android.domain.repository.moreview.MoreNewViewRepository


class MoreNewViewRepositoryImpl(private val dataSource: RemoteMoreNewViewDataSource) :
    MoreNewViewRepository {
    override suspend fun getMoreNewView(
        userEmail: String,
        identifer: String,
        value: String
    ): ResponseMoreViewData = dataSource.getMoreNewView(userEmail, identifer, value)
}