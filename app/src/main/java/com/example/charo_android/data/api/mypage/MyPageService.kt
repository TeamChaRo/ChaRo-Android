package com.example.charo_android.data.api.mypage

import com.example.charo_android.data.model.mypage.ResponseEndlessScroll
import com.example.charo_android.data.model.mypage.ResponseMyPage
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageService {
    @GET("user/myPage/like/{userEmail}")
    suspend fun getLikePost(
        @Path("userEmail") userEmail: String
    ): ResponseMyPage

    @GET("user/myPage/new/{userEmail}")
    suspend fun getNewPost(
        @Path("userEmail") userEmail: String
    ): ResponseMyPage

    @GET("user/myPage/like/{userEmail}/write/{lastId}/{lastCount}")
    suspend fun getMoreWrittenLikePost(
        @Path("userEmail") userEmail: String,
        @Path("lastId") lastId: Int,
        @Path("lastCount") lastCount: Int
    ): ResponseEndlessScroll

    @GET("user/myPage/new/{userEmail}/write/{lastId}")
    suspend fun getMoreWrittenNewPost(
        @Path("userEmail") userEmail: String,
        @Path("lastId") lastId: Int
    ): ResponseEndlessScroll

    @GET("user/myPage/like/{userEmail}/save/{lastId}/{lastCount}")
    suspend fun getMoreSavedLikePost(
        @Path("userEmail") userEmail: String,
        @Path("lastId") lastId: Int,
        @Path("lastCount") lastCount: Int
    ): ResponseEndlessScroll

    @GET("user/myPage/new/{userEmail}/save/{lastId}")
    suspend fun getMoreSavedNewPost(
        @Path("userEmail") userEmail: String,
        @Path("lastId") lastId: Int
    ): ResponseEndlessScroll
}