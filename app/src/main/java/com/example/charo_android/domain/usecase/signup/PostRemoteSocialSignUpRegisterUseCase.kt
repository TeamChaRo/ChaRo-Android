package com.example.charo_android.domain.usecase.signup

import com.example.charo_android.data.model.request.signup.RequestSignUpGoogleData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository

class PostRemoteSocialSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {

    suspend fun execute(requestSignUpSocialData: RequestSignUpGoogleData) : StatusCode{
        return repository.signUpGoogleRegister(requestSignUpSocialData)

    }
}