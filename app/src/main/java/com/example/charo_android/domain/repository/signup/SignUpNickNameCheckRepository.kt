package com.example.charo_android.domain.repository.signup

import com.example.charo_android.data.model.response.signup.ResponseNickNameCheckData

interface SignUpNickNameCheckRepository {
    suspend fun nickNameCheck(nickname: String) : ResponseNickNameCheckData
}