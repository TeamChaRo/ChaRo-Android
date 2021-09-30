package com.example.charo_android.data.api.signup

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpEmailCheckViewService {
    @GET("/user/check/{userEmail}")
    suspend fun emailCheck(
        @Path("userEmail") userEmail: String
    ) : ResponseEmailCheckData
}