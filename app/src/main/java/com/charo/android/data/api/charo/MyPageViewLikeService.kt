package com.charo.android.data.api.charo


import com.charo.android.data.model.charo.ResponseMyPageLikeData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageViewLikeService {
    @GET("/user/myPage/like/{userEmail}")
    fun getMyPage(
        @Path("userEmail") userEmail: String
    ): Call<ResponseMyPageLikeData>
}