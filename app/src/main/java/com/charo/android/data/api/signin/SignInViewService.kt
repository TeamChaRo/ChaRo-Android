package com.charo.android.data.api.signin


import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.data.model.response.signin.ResponseSignInData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInViewService {
    @POST("/user/login")
    suspend fun postSignIn(
        @Body body: RequestSignInData
    ): ResponseSignInData
}