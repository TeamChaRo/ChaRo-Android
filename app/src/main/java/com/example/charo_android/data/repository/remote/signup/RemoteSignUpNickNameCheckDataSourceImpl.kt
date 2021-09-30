package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.api.signup.SignUpNickNameCheckViewService
import com.example.charo_android.data.model.response.signup.ResponseNickNameCheckData

class RemoteSignUpNickNameCheckDataSourceImpl(val service: SignUpNickNameCheckViewService)
    :RemoteSignUpNickNameCheckDataSource{
    override suspend fun nickNameCheck(nickname: String): ResponseNickNameCheckData = service.nickNameCheck(nickname)
}