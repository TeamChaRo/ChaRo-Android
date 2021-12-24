package com.example.charo_android.domain.usecase.signup

import com.example.charo_android.data.model.request.signup.RequestSignUpSocialData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.model.signup.SocialSignUp
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository

class PostRemoteSocialSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {

    suspend fun execute(requestSignUpSocialData: RequestSignUpSocialData) : StatusCode{
        return repository.signUpGoogleRegister(requestSignUpSocialData)

    }
}