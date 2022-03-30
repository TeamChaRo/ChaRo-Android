package com.charo.android.data.api.signup

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpEmailCheckViewService {
    @GET("/user/check/email/{userEmail}")
    suspend fun emailCheck(
        @Path("userEmail") userEmail: String
    ) : ResponseEmailCheckData
}