package com.example.charo_android.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoreViewService {
    @GET("/preview/like/{userId}/{identifier}")
    fun getPreview(
        @Path("userId") userId: String,
        @Path("identifier") identifier: String,
        @Query("value") value:String
    ) : Call<ResponseMoreViewData>
}