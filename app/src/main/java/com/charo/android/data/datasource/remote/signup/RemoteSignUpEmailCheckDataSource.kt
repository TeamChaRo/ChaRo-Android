package com.charo.android.data.datasource.remote.signup

import com.charo.android.data.model.response.signup.ResponseEmailCheckData


interface RemoteSignUpEmailCheckDataSource {
    suspend fun emailCheck(email: String): ResponseEmailCheckData
}