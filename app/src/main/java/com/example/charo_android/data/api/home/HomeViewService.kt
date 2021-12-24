package com.example.charo_android.data.api.home

import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.home.ResponseHomeViewData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeViewService {
    @GET("/post/main/{userEmail}")
    suspend fun getMain(
        @Path("userEmail") userEmail : String
    ) : ResponseHomeViewData

    @POST("/post/like")
    suspend fun postLike(
        @Body body : RequestHomeLikeData
    ) : ResponseStatusCode
}