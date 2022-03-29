package com.charo.android.data.datasource.remote.detailpost

import com.example.charo_android.data.api.detailpost.DetailPostService
import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import com.example.charo_android.data.model.detailpost.ResponseDetailPostLikeUserList

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
}