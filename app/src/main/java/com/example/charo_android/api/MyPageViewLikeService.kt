package com.example.charo_android.api

import com.example.charo_android.data.ResponseMyPageLikeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewLikeService {
    @GET("/myPage/like/{userId}")
    fun getMyPageLike(
        @Path("userId") userId: String
    ): Call<ResponseMyPageLikeData>
}