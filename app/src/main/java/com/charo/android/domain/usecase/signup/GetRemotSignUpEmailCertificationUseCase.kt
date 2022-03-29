package com.charo.android.domain.usecase.signup

import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.domain.repository.signup.SignUpEmailCertificationRepository

class GetRemoteSignUpEmailCertificationUseCase(private val repository: SignUpEmailCertificationRepository) {

    suspend fun execute(userEmail: String) : String{
        return SignUpMapper.mapperToEmailCertification(repository.emailCertification(
            RequestCertificationData(userEmail)
        ))
    }
}