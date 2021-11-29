package com.example.charo_android.data.repository

import com.example.charo_android.data.model.response.more.ResponseMoreViewInfiniteData
import com.example.charo_android.data.repository.remote.more.RemoteMoreViewInfiniteDataSource
import com.example.charo_android.domain.repository.moreview.MoreViewInfiniteRepository

class MoreViewInfiniteRepositoryImpl(private val dataSource: RemoteMoreViewInfiniteDataSource) :
    MoreViewInfiniteRepository {
    override suspend fun getPreview(
        userId: String,
        identifer: String,
        postId: Int,
        count: Int,
        value: String
    ): ResponseMoreViewInfiniteData {
        return dataSource.getPreview(userId, identifer, postId, count, value )
    }
}