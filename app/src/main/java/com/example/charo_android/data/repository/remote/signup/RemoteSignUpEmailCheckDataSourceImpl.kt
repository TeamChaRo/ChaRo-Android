package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.api.SignUpEmailCheckViewService
import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email

class RemoteSignUpEmailCheckDataSourceImpl(private val service: SignUpEmailCheckViewService):
    RemoteSignUpEmailCheckDataSource {
    override suspend fun emailCheck(email : Email): ResponseEmailCheckData = service.emailCheck(email)
}