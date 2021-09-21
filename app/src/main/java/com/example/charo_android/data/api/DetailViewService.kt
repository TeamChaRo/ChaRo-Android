package com.example.charo_android.data.api

import com.example.charo_android.data.model.response.ResponseDetailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailViewService {
    @GET("/postDetail/{userId}/{postId}")
    fun getDetail(
        @Path("userId") userId: String, @Path("postId") postId: Int
//        @Query("userId", encoded=true) userId: String, @Query("postId", encoded=true) postId: String
    ): Call<ResponseDetailData>
}