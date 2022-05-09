package com.charo.android.data.api.signin


import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.data.model.response.ResponseDefaultData
import com.charo.android.data.model.response.signin.ResponseSignInData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SignInViewService {
    @POST("/user/login")
    suspend fun postSignIn(
        @Body body: RequestSignInData
    ): ResponseSignInData

    //비밀번호 찾기
    @GET("/user/password/{userEmail}")
    suspend fun getPasswordSearch(
        @Path("userEmail") userEmail : String
    ): ResponseDefaultData
}