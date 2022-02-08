package com.example.charo_android.domain.repository.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList

interface FollowRepository {
    suspend fun getFollowList(userEmail: String, myPageEmail: String): ResponseFollowList
}