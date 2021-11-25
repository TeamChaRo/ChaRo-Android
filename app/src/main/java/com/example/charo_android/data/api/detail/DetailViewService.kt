package com.example.charo_android.data.api.detail

import com.example.charo_android.data.model.detail.RequestDetailLikeAndSave
import com.example.charo_android.data.model.detail.ResponseDetailData
import com.example.charo_android.data.model.detail.ResponseDetailLikeAndSave
import com.example.charo_android.data.model.detail.ResponseDetailLikes
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
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
}