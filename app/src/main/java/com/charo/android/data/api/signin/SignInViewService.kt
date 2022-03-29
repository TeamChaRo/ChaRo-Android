package com.charo.android.data.api.signin

import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.data.model.response.signin.ResponseSignInData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInViewService {
    @POST("/user/login")
    suspend fun postSignIn(
        @Body body: RequestSignInData
    ): ResponseSignInData
}