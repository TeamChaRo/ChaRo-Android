package com.example.charo_android.data.api

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpEmailCheckViewService {
    @POST("/user/register/email")
    suspend fun emailCheck(
        @Body body : Email
    ) : ResponseEmailCheckData
}