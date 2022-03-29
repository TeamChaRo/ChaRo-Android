package com.charo.android.data.datasource.remote.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList
import com.example.charo_android.data.model.request.follow.RequestPostFollowData
import com.example.charo_android.data.model.response.follow.ResponsePostFollowData

interface RemoteFollowDataSource {
    suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList
    suspend fun postFollow(requestPostFollowData: RequestPostFollowData): ResponsePostFollowData
}