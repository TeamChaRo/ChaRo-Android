package com.example.charo_android.data.api.more

import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoreViewService {
    @GET("/post/preview/like/{userEmail}/{identifier}")
    suspend fun getMoreView(
        @Path("userEmail") userEmail : String,
        @Path("identifier") identifier : String,
        @Query("value") value : String
    ) : ResponseMoreViewData
}