package com.example.charo_android.api

import com.example.charo_android.data.RequestSignInData
import com.example.charo_android.data.ResponseSignInData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignInViewService {
    @Headers ("accept: application/json",
        "content-type: application/json")
    @POST("/sign/signIn")
    fun postSignIn(
        @Body body: RequestSignInData
    ): Call<ResponseSignInData>
}