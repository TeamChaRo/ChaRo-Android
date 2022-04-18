package com.charo.android.data.datasource.repositoryimpl.detailpost

import com.charo.android.data.datasource.remote.detailpost.RemoteDetailPostDataSource
import com.charo.android.data.mapper.DetailPostMapper
import com.charo.android.data.model.detailpost.RequestDeleteDetailPost
import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.domain.model.detailpost.User
import com.charo.android.domain.repository.detailpost.DetailPostRepository


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

    override suspend fun deleteDetailPost(postId: Int, images: List<String>): Boolean {
        return dataSource.deleteDetailPost(postId, RequestDeleteDetailPost(images)).toBoolean()
    }
}