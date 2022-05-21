package com.charo.android.domain.usecase.signup

import com.charo.android.data.model.request.signup.RequestSignUpKaKaoData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.signup.SignUpRegisterRepository


class PostRemoteKaKaoSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {

    suspend operator fun invoke(requestSignUpKaKaoData: RequestSignUpKaKaoData) : StatusCode {
        return repository.signUpKaKaoRegister(requestSignUpKaKaoData)

    }
}