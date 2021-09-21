package com.example.charo_android.data.api

import com.example.charo_android.data.model.response.ResponseHomeViewData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeViewService {
    @GET("/post/main/{userEmail}")
    suspend fun getMain(
        @Path("userEmail") userEmail : String
    ) : ResponseHomeViewData
}