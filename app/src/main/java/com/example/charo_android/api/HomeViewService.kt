package com.example.charo_android.api

import com.example.charo_android.data.response.ResponseHomeViewData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeViewService {
    @GET("/getMain/{id}")
    suspend fun getMain(
        @Path("id") id : String
    ) : ResponseHomeViewData
}