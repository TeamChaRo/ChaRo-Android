package com.example.charo_android.domain.repository.signup

import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData

interface SignUpEmailCertificationRepository {
    suspend fun emailCertification(userEmail: RequestCertificationData) : ResponseCertificationData
}