package com.charo.android.data.datasource.remote.follow

import com.example.charo_android.data.api.follow.FollowService
import com.example.charo_android.data.model.mypage.ResponseFollowList
import com.example.charo_android.data.model.request.follow.RequestPostFollowData
import com.example.charo_android.data.model.response.follow.ResponsePostFollowData

class RemoteFollowDataSourceImpl(private val service: FollowService): RemoteFollowDataSource {
    override suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList {
        return service.getFollowList(userEmail, myPageEmail)
    }

    override suspend fun postFollow(requestPostFollowData: RequestPostFollowData): ResponsePostFollowData {
        return service.postFollow(requestPostFollowData)
    }
}