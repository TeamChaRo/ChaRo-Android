package com.example.charo_android.data.repository.remote.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList

interface RemoteFollowDataSource {
    suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList
}