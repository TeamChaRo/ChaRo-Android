package com.example.charo_android.api

import com.example.charo_android.data.ResponseDetailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailViewService {
    @GET("/postDetail/{userId}/{postId}")
    fun getDetail(
        @Path("userId") userId: String, @Path("postId") postId: String
//        @Query("userId", encoded=true) userId: String, @Query("postId", encoded=true) postId: String
    ): Call<ResponseDetailData>
}