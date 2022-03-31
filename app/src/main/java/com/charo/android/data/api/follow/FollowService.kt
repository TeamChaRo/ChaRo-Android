package com.charo.android.data.api.follow


import com.charo.android.data.model.mypage.ResponseFollowList
import com.charo.android.data.model.request.follow.RequestPostFollowData
import com.charo.android.data.model.response.follow.ResponsePostFollowData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FollowService {
    @GET("user/follow")
    suspend fun getFollowList(
        @Query("userEmail") userEmail: String,
        @Query("myPageEmail") myPageEmail: String
    ): ResponseFollowList

    @POST("user/follow")
    suspend fun postFollow(
        @Body body: RequestPostFollowData
    ): ResponsePostFollowData
}