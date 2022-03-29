package com.charo.android.data.datasource.remote.signup

import com.example.charo_android.data.api.signup.SignUpEmailCertificationViewService
import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData

class RemoteSignUpEmailCertificationDataSourceImpl(private val service: SignUpEmailCertificationViewService)
    :RemoteSignUpEmailCertificationDataSource {

    override suspend fun emailCertification(userEmail: RequestCertificationData): ResponseCertificationData
    = service.emailCertification(userEmail)
}