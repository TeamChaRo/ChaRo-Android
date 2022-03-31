package com.charo.android.data.datasource.remote.follow

import com.charo.android.data.model.mypage.ResponseFollowList
import com.charo.android.data.model.request.follow.RequestPostFollowData
import com.charo.android.data.model.response.follow.ResponsePostFollowData


interface RemoteFollowDataSource {
    suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList
    suspend fun postFollow(requestPostFollowData: RequestPostFollowData): ResponsePostFollowData
}