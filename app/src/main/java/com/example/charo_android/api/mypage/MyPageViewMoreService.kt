package com.example.charo_android.api.mypage

import com.example.charo_android.data.mypage.ResponseMyPageMoreData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewMoreService {
    @GET("/user/myPage/like/{userEmail}/write/{postId}/{count}")
    fun getMoreData (
        @Path("userEmail") userEmail: String,
        @Path("postId") postId: Int,
        @Path("count") count: Int
    ): Call<ResponseMyPageMoreData>
}