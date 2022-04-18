package com.charo.android.data.datasource.remote.detailpost

import com.charo.android.data.api.detailpost.DetailPostService
import com.charo.android.data.model.detailpost.RequestDeleteDetailPost
import com.charo.android.data.model.detailpost.ResponseDeleteDetailPost
import com.charo.android.data.model.detailpost.ResponseDetailPost
import com.charo.android.data.model.detailpost.ResponseDetailPostLikeUserList


class RemoteDetailPostDataSourceImpl(private val service: DetailPostService): RemoteDetailPostDataSource {
    override suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost {
        return service.getDetailPost(userEmail, postId)
    }

    override suspend fun getDetailPostLikeUserList(
        postId: Int,
        userEmail: String
    ): ResponseDetailPostLikeUserList {
        return service.getDetailPostLikeUserList(postId, userEmail)
    }

    override suspend fun deleteDetailPost(
        postId: Int,
        requestBody: RequestDeleteDetailPost
    ): ResponseDeleteDetailPost {
        return service.deleteDetailPost(postId, requestBody)
    }
}