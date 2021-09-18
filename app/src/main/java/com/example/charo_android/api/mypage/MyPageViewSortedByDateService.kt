package com.example.charo_android.api.mypage

import com.example.charo_android.data.mypage.ResponseMyPageSortedByDateData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewSortedByDateService {
    @GET("/user/myPage/new/{userEmail}")
    fun getMyPage(
        @Path("userEmail") userEmail: String
    ): Call<ResponseMyPageSortedByDateData>
}