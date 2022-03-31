package com.charo.android.domain.repository.signup

import com.charo.android.data.model.request.RequestCertificationData
import com.charo.android.data.model.response.signup.ResponseCertificationData


interface SignUpEmailCertificationRepository {
    suspend fun emailCertification(userEmail: RequestCertificationData) : ResponseCertificationData
}