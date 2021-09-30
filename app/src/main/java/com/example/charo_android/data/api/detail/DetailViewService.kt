package com.example.charo_android.data.api.detail

import com.example.charo_android.data.model.detail.RequestDetailLikeAndSave
import com.example.charo_android.data.model.detail.ResponseDetailData
import com.example.charo_android.data.model.detail.ResponseDetailLikeAndSave
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailViewService {
    @GET("/post/detail/{userEmail}/{postId}")
    fun getDetail(
        @Path("userEmail") userEmail: String, @Path("postId") postId: Int
    ): Call<ResponseDetailData>

    @POST("/post/like")
    fun postDetailLike(
        @Body body: RequestDetailLikeAndSave
    ): Call<ResponseDetailLikeAndSave>

    @POST("/post/save")
    fun postDetailSave(
        @Body body: RequestDetailLikeAndSave
    ): Call<ResponseDetailLikeAndSave>
}