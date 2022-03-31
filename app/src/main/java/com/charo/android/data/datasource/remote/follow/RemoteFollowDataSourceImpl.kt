package com.charo.android.data.datasource.remote.follow

import com.charo.android.data.api.follow.FollowService
import com.charo.android.data.model.mypage.ResponseFollowList
import com.charo.android.data.model.request.follow.RequestPostFollowData
import com.charo.android.data.model.response.follow.ResponsePostFollowData


class RemoteFollowDataSourceImpl(private val service: FollowService): RemoteFollowDataSource {
    override suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList {
        return service.getFollowList(userEmail, myPageEmail)
    }

    override suspend fun postFollow(requestPostFollowData: RequestPostFollowData): ResponsePostFollowData {
        return service.postFollow(requestPostFollowData)
    }
}