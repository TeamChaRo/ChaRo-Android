package com.example.charo_android.data.repository.remote.follow

import com.example.charo_android.data.api.follow.FollowService
import com.example.charo_android.data.model.mypage.ResponseFollowList

class RemoteFollowDataSourceImpl(private val service: FollowService): RemoteFollowDataSource {
    override suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList {
        return service.getFollowList(userEmail, myPageEmail)
    }
}