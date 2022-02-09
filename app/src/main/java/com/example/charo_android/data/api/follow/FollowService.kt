package com.example.charo_android.data.api.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowService {
    @GET("user/follow")
    suspend fun getFollowList(
        @Query("userEmail") userEmail: String,
        @Query("myPageEmail") myPageEmail: String
    ): ResponseFollowList
}