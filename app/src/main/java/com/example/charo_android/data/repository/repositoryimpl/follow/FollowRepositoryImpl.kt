package com.example.charo_android.data.repository.repositoryimpl.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList
import com.example.charo_android.data.repository.remote.follow.RemoteFollowDataSource
import com.example.charo_android.domain.repository.follow.FollowRepository

class FollowRepositoryImpl(private val remoteDataSource: RemoteFollowDataSource): FollowRepository {
    override suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList {
        return remoteDataSource.getFollowList(userEmail, myPageEmail)
    }
}