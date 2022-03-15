package com.example.charo_android.data.api.more


import com.example.charo_android.data.model.response.more.ResponseMoreViewInfiniteData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoreViewInfiniteService {
    @GET("/post/preview/like/{userEmail}/{identifier}/{postId}/{count}")
    suspend fun getPreview(
        @Path("userEmail") userEmail: String,
        @Path("identifier") identifier: String,
        @Path("postId") postId: Int,
        @Path("count") count: Int,
        @Query("value") value:String
    ) : ResponseMoreViewInfiniteData



    @GET("/post/preview/new/{userEmail}/{identifier}/{postId}")
    suspend fun getNewPreview(
        @Path("userEmail") userEmail: String,
        @Path("identifier") identifier: String,
        @Path("postId") postId: Int,
        @Query("value") value: String
    ) : ResponseMoreViewInfiniteData
}