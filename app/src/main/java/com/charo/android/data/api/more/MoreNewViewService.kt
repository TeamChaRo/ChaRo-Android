package com.charo.android.data.api.more


import com.charo.android.data.model.response.more.ResponseMoreViewData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoreNewViewService {
    @GET("/post/preview/new/{userEmail}/{identifier}")
    suspend fun getMoreNewView(
        @Path("userEmail") userEmail : String,
        @Path("identifier") identifier : String,
        @Query("value") value : String
    ) : ResponseMoreViewData
}