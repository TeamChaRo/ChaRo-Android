package com.example.charo_android.data.repository.remote.more

import com.example.charo_android.data.model.response.more.ResponseMoreViewInfiniteData

interface RemoteMoreViewInfiniteDataSource {
    suspend fun getPreview(
        userId: String,
        identifer: String,
        postId: Int,
        count: Int,
        value: String
    )
            : ResponseMoreViewInfiniteData

    suspend fun getNewPreview(
        userId: String,
        identifer: String,
        postId: Int,
        value: String
    )
            : ResponseMoreViewInfiniteData

}