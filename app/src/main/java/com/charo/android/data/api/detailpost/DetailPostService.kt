package com.charo.android.data.api.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import com.example.charo_android.data.model.detailpost.ResponseDetailPostLikeUserList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailPostService {
    @GET("post/detail/{userEmail}/{postId}")
    suspend fun getDetailPost(
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int
    ): ResponseDetailPost

    @GET("post/likes/{postId}")
    suspend fun getDetailPostLikeUserList(
        @Path("postId") postId: Int,
        @Query("userEmail") userEmail: String
    ): ResponseDetailPostLikeUserList
}