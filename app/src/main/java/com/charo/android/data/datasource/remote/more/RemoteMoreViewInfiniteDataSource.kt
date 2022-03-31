package com.charo.android.data.datasource.remote.more

import com.charo.android.data.model.response.more.ResponseMoreViewInfiniteData


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