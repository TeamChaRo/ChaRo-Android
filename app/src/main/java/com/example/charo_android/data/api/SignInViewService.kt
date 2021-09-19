package com.example.charo_android.data.api

import com.example.charo_android.data.model.request.RequestSignInData
import com.example.charo_android.data.model.response.ResponseSignInData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignInViewService {
    @POST("/user/login")
    fun postSignIn(
        @Body body: RequestSignInData
    ): Call<ResponseSignInData>
}