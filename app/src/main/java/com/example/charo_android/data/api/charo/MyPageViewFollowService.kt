package com.example.charo_android.data.api.charo

import com.example.charo_android.data.model.charo.ResponseMyPageFollowData
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