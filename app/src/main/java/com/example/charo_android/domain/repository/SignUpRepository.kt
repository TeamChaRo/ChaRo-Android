package com.example.charo_android.domain.repository

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email

interface SignUpRepository {
    suspend fun emailCheck(email: Email) : ResponseEmailCheckData
}