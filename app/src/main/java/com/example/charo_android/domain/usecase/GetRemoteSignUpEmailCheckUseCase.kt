package com.example.charo_android.domain.usecase

import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.domain.model.signup.EmailCheck
import com.example.charo_android.domain.repository.SignUpRepository

class GetRemoteSignUpEmailCheckUseCase(private val repository: SignUpRepository) {
    suspend fun execute(email: Email): Boolean{
        return SignUpMapper.mapperToEmailCheck(repository.emailCheck(email))
    }
}