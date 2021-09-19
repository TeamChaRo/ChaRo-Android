package com.example.charo_android.data.api

import com.example.charo_android.data.model.response.ResponseMyPageLikeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewLikeService {
    @GET("/myPage/like/{userId}")
    fun getMyPageLike(
        @Path("userId") userId: String
    ): Call<ResponseMyPageLikeData>
}