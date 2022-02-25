package com.example.charo_android.data.datasource.repositoryimpl.signup

import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData
import com.example.charo_android.data.datasource.remote.signup.RemoteSignUpEmailCertificationDataSource
import com.example.charo_android.domain.repository.signup.SignUpEmailCertificationRepository

class SignUpEmailCertificationRepositoryImpl(private val dataSource: RemoteSignUpEmailCertificationDataSource)
    :SignUpEmailCertificationRepository {
    override suspend fun emailCertification(userEmail: RequestCertificationData): ResponseCertificationData {
        return dataSource.emailCertification(userEmail)
    }
}