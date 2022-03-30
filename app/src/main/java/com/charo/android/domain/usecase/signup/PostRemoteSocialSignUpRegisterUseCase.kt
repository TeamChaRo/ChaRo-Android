package com.charo.android.domain.usecase.signup

import com.charo.android.data.model.request.signup.RequestSignUpGoogleData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.signup.SignUpRegisterRepository


class PostRemoteSocialSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {

    suspend fun execute(requestSignUpSocialData: RequestSignUpGoogleData) : StatusCode {
        return repository.signUpGoogleRegister(requestSignUpSocialData)

    }
}