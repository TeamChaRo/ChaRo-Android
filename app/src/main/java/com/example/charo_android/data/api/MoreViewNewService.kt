package com.example.charo_android.data.api

import com.example.charo_android.data.model.response.ResponseMoreViewData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoreViewNewService {
    @GET("/preview/new/{userId}/{identifier}")
    fun getNewPreview(
        @Path("userId") userId: String,
        @Path("identifier") identifier: String,
        @Query("value") value:String
    ) : Call<ResponseMoreViewData>
}