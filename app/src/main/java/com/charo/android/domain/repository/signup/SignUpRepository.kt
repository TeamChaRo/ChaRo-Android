package com.charo.android.domain.repository.signup

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData

interface SignUpRepository {
    suspend fun emailCheck(email: String) : ResponseEmailCheckData
}