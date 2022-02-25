package com.example.charo_android.data.datasource.repositoryimpl.more

import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import com.example.charo_android.data.datasource.remote.more.RemoteMoreViewDataSource
import com.example.charo_android.domain.repository.moreview.MoreViewRepository

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