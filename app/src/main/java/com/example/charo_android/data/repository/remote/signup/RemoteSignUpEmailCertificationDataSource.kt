package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData

interface RemoteSignUpEmailCertificationDataSource {
    suspend fun emailCertification(userEmail: RequestCertificationData) : ResponseCertificationData
}