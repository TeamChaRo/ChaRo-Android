package com.charo.android.domain.usecase.signup

import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.domain.repository.signup.SignUpNickNameCheckRepository

class GetRemoteSignUpNickNameCheckUseCase(private val repository: SignUpNickNameCheckRepository) {
    suspend fun execute(nickname: String): Boolean{
        return SignUpMapper.mapperToNickNameCheck(repository.nickNameCheck(nickname))
    }
}