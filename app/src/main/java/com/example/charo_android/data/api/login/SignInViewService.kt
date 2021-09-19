package com.example.charo_android.data.api.login

import com.example.charo_android.data.model.login.RequestSignInData
import com.example.charo_android.data.model.login.ResponseSignInData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignInViewService {
    @Headers ("accept: application/json",
        "content-type: application/json")
    @POST("/user/login")
    fun postSignIn(
        @Body body: RequestSignInData
    ): Call<ResponseSignInData>
}