package com.charo.android.data.datasource.repositoryimpl.follow

import com.charo.android.data.datasource.remote.follow.RemoteFollowDataSource
import com.charo.android.data.model.mypage.ResponseFollowList
import com.charo.android.data.model.request.follow.RequestPostFollowData
import com.charo.android.domain.repository.follow.FollowRepository


class FollowRepositoryImpl(private val remoteDataSource: RemoteFollowDataSource) :
    FollowRepository {
    override suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList {
        return remoteDataSource.getFollowList(userEmail, myPageEmail)
    }

    override suspend fun postFollow(follower: String, followed: String): Boolean {
        return remoteDataSource.postFollow(
            RequestPostFollowData(follower, followed)
        ).data.isFollow
    }
}