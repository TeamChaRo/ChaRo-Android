package com.example.charo_android.api.mypage

import com.example.charo_android.data.mypage.ResponseMyPageSortedByPopularData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewSortedByPopularService {
    @GET("/user/myPage/like/{userEmail}")
    fun getMyPage(
        @Path("userEmail") userEmail: String
    ): Call<ResponseMyPageSortedByPopularData>
}