package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email

interface RemoteSignUpEmailCheckDataSource {
    suspend fun emailCheck(email: String): ResponseEmailCheckData
}