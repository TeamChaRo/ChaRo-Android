package com.charo.android.data.api.signup

import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpEmailCertificationViewService {
    @POST("/user/auth")
    suspend fun emailCertification(
        @Body userEmail: RequestCertificationData
    ) : ResponseCertificationData
}