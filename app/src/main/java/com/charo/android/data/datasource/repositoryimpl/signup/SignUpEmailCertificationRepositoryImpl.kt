package com.charo.android.data.datasource.repositoryimpl.signup

import com.charo.android.data.datasource.remote.signup.RemoteSignUpEmailCertificationDataSource
import com.charo.android.data.model.request.RequestCertificationData
import com.charo.android.data.model.response.signup.ResponseCertificationData
import com.charo.android.domain.repository.signup.SignUpEmailCertificationRepository


class SignUpEmailCertificationRepositoryImpl(private val dataSource: RemoteSignUpEmailCertificationDataSource)
    : SignUpEmailCertificationRepository {
    override suspend fun emailCertification(userEmail: RequestCertificationData): ResponseCertificationData {
        return dataSource.emailCertification(userEmail)
    }
}