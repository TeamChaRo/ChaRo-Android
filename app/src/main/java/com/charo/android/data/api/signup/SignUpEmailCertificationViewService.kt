package com.charo.android.data.api.signup


import com.charo.android.data.model.request.RequestCertificationData
import com.charo.android.data.model.response.signup.ResponseCertificationData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpEmailCertificationViewService {
    @POST("/user/auth")
    suspend fun emailCertification(
        @Body userEmail: RequestCertificationData
    ) : ResponseCertificationData
}