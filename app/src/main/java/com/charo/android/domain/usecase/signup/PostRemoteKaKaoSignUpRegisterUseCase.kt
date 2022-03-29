package com.charo.android.domain.usecase.signup

import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.data.model.request.signup.RequestSignUpKaKaoData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository

class PostRemoteKaKaoSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {

    suspend fun execute(requestSignUpKaKaoData: RequestSignUpKaKaoData) : StatusCode{
        return repository.signUpKaKaoRegister(requestSignUpKaKaoData)

    }
}