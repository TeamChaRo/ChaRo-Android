package com.example.charo_android.api

import com.example.charo_android.data.ResponseMyPageNewData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewNewService {
    @GET("/myPage/new/{userId}")
    fun getMyPageNew(
        @Path("userId") userId: String
    ): Call<ResponseMyPageNewData>
}

//    @GET("/myPage/like/{userId}")
//    fun getMyPgaeLike(
//        @Path("userId") userId: String
//    ): Call<ResponseMyPageLikeData>