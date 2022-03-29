package com.charo.android.data.datasource.remote.signup

import com.example.charo_android.data.model.response.signup.ResponseNickNameCheckData

interface RemoteSignUpNickNameCheckDataSource {
    suspend fun nickNameCheck(nickname: String) : ResponseNickNameCheckData
}