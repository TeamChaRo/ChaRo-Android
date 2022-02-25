package com.example.charo_android.data.datasource.repositoryimpl.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import com.example.charo_android.data.datasource.remote.detailpost.RemoteDetailPostDataSource
import com.example.charo_android.domain.repository.detailpost.DetailPostRepository

class DetailPostRepositoryImpl(private val dataSource: RemoteDetailPostDataSource): DetailPostRepository {
    override suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost {
        return dataSource.getDetailPost(userEmail, postId)
    }
}