package com.charo.android.domain.usecase.signup

import com.charo.android.data.mapper.SignUpMapper
import com.charo.android.domain.repository.signup.SignUpRepository


class GetRemoteSignUpEmailCheckUseCase(private val repository: SignUpRepository) {
    suspend fun execute(email: String): Boolean{
        return SignUpMapper.mapperToEmailCheck(repository.emailCheck(email))
    }
}