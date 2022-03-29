package com.charo.android.data.datasource.repositoryimpl.detailpost

import com.example.charo_android.data.datasource.remote.detailpost.RemoteDetailPostDataSource
import com.example.charo_android.data.mapper.DetailPostMapper
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.model.detailpost.User
import com.example.charo_android.domain.repository.detailpost.DetailPostRepository

class DetailPostRepositoryImpl(private val dataSource: RemoteDetailPostDataSource) :
    DetailPostRepository {
    override suspend fun getDetailPost(userEmail: String, postId: Int): DetailPost {
        return DetailPostMapper.mapperToDetailPost(dataSource.getDetailPost(userEmail, postId))
    }

    override suspend fun getDetailPostLikeUserList(postId: Int, userEmail: String): List<User> {
        return dataSource.getDetailPostLikeUserList(postId, userEmail).data.map {
            User(
                it.nickname,
                it.userEmail,
                it.image,
                it.isFollow
            )
        }
    }
}