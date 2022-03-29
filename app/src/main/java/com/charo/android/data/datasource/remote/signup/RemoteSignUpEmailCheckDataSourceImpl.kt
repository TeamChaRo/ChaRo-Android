package com.charo.android.data.datasource.remote.signup

import com.example.charo_android.data.api.signup.SignUpEmailCheckViewService
import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData

class RemoteSignUpEmailCheckDataSourceImpl(private val service: SignUpEmailCheckViewService):
    RemoteSignUpEmailCheckDataSource {
    override suspend fun emailCheck(email : String): ResponseEmailCheckData = service.emailCheck(email)
}