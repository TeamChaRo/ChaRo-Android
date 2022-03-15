package com.example.charo_android.data.repository.remote.more

import com.example.charo_android.data.api.more.MoreViewInfiniteService
import com.example.charo_android.data.model.response.more.ResponseMoreViewInfiniteData

class RemoteMoreViewInfiniteDataSourceImpl(private val service: MoreViewInfiniteService): RemoteMoreViewInfiniteDataSource {
    override suspend fun getPreview(
        userId: String,
        identifer: String,
        postId: Int,
        count: Int,
        value: String
    ) : ResponseMoreViewInfiniteData = service.getPreview(userId,
        identifer,
        postId,
        count,
        value)

    override suspend fun getNewPreview(
        userId: String,
        identifer: String,
        postId: Int,
        value: String
    ): ResponseMoreViewInfiniteData {
        return service.getNewPreview(userId,
            identifer,
            postId,
            value
        )
    }
}