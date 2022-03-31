package com.charo.android.domain.repository.signup

import com.charo.android.data.model.response.signup.ResponseEmailCheckData


interface SignUpRepository {
    suspend fun emailCheck(email: String) : ResponseEmailCheckData
}