package com.example.charo_android.data.api.detail

import com.example.charo_android.data.model.detail.ResponseDetailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailViewService {
    @GET("/post/detail/{userEmail}/{postId}")
    fun getDetail(
        @Path("userEmail") userEmail: String, @Path("postId") postId: String
    ): Call<ResponseDetailData>
}