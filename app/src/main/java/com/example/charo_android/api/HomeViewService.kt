package com.example.charo_android.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface HomeViewService {
    @GET("/getMain/{id}")
    fun getMain(
        @Path("id") id : String
    ) : Call<ResponseHomeViewData>
}