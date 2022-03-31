package com.charo.android.data.api.charo


import com.charo.android.data.model.charo.ResponseMyPageFollowData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageViewFollowService {
    @GET("/user/follow")
    fun getFollowInfo(
        @Query("userEmail") userEmail: String,
        @Query("myPageEmail") myPageEmail: String
    ): Call<ResponseMyPageFollowData>
}