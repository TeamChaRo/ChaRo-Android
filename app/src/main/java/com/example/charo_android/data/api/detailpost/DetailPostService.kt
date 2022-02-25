package com.example.charo_android.data.api.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailPostService {
    @GET("post/detail/{userEmail}/{postId}")
    suspend fun getDetailPost(
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int
    ): ResponseDetailPost
}