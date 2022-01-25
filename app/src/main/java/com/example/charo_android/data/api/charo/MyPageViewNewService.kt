package com.example.charo_android.data.api.charo

import com.example.charo_android.data.model.charo.ResponseMyPageLikeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewNewService {
    @GET("/user/myPage/new/{userEmail}")
    fun getMyPage(
        @Path("userEmail") userEmail: String
    ): Call<ResponseMyPageLikeData>
}