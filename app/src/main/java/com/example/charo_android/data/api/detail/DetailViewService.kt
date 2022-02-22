package com.example.charo_android.data.api.detail

import com.example.charo_android.data.model.detailold.*
import retrofit2.Call
import retrofit2.http.*

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

    @GET("/post/likes/{postId}")
    fun getLikes(
        @Path("postId") postId: Int, @Query("userEmail") userEmail: String
    ): Call<ResponseDetailLikes>

    @HTTP(method = "DELETE", path = "/post/{postId}", hasBody = true)
    fun deletePost(
        @Path("postId") postId: Int, @Body body: RequestDetailDeleteData
    ): Call<ResponseDetailDeleteData>
}