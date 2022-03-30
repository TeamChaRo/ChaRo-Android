package com.charo.android.domain.usecase.signup

import com.charo.android.data.mapper.SignUpMapper
import com.charo.android.data.model.request.RequestCertificationData
import com.charo.android.domain.repository.signup.SignUpEmailCertificationRepository


class GetRemoteSignUpEmailCertificationUseCase(private val repository: SignUpEmailCertificationRepository) {

    suspend fun execute(userEmail: String) : String{
        return SignUpMapper.mapperToEmailCertification(repository.emailCertification(
            RequestCertificationData(userEmail)
        ))
    }
}