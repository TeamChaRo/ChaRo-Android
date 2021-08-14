package com.example.charo_android.api

import com.example.charo_android.data.request.RequestDetailLikeData
import com.example.charo_android.data.response.ResponseDetailLikeData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DetailViewLikeService {
    @POST("/post/like")
    fun postDetailLike(
        @Body body: RequestDetailLikeData
    ): Call<ResponseDetailLikeData>
}