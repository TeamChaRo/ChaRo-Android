package com.example.charo_android.data.datasource.remote.detailpost

import com.example.charo_android.data.api.detailpost.DetailPostService
import com.example.charo_android.data.model.detailpost.ResponseDetailPost

class RemoteDetailPostDataSourceImpl(private val service: DetailPostService): RemoteDetailPostDataSource {
    override suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost {
        return service.getDetailPost(userEmail, postId)
    }
}