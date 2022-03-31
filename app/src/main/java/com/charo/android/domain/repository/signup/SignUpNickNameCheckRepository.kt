package com.charo.android.domain.repository.signup

import com.charo.android.data.model.response.signup.ResponseNickNameCheckData


interface SignUpNickNameCheckRepository {
    suspend fun nickNameCheck(nickname: String) : ResponseNickNameCheckData
}