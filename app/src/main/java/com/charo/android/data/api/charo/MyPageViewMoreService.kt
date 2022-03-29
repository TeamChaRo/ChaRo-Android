package com.charo.android.data.api.charo

import com.example.charo_android.data.model.charo.ResponseMyPageMoreData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewMoreService {
    @GET("/user/myPage/like/{userEmail}/write/{postId}/{count}")
    fun getMoreWrittenLikeData (
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int,
        @Path("count") count: Int
    ): Call<ResponseMyPageMoreData>

    @GET("/user/myPage/like/{userEmail}/save/{postId}/{count}")
    fun getMoreSavedLikeData (
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int,
        @Path("count") count: Int
    ): Call<ResponseMyPageMoreData>

    @GET("/user/myPage/new/{userEmail}/write/{postId}")
    fun getMoreWrittenNewData (
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int,
    ): Call<ResponseMyPageMoreData>

    @GET("/user/myPage/new/{userEmail}/save/{postId}")
    fun getMoreSavedNewData (
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int,
    ): Call<ResponseMyPageMoreData>
}