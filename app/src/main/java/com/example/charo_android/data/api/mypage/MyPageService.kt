package com.example.charo_android.data.api.mypage

import com.example.charo_android.data.model.mypage.ResponseLikePost
import com.example.charo_android.data.model.mypage.ResponseNewPost
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageService {
    @GET("user/myPage/like/{userEmail}")
    suspend fun getLikePost(
        @Path("userEmail") userEmail: String
    ): ResponseLikePost

    @GET("user/myPage/new/{userEmail}")
    suspend fun getNewPost(
        @Path("userEmail") userEmail: String
    ): ResponseNewPost
}