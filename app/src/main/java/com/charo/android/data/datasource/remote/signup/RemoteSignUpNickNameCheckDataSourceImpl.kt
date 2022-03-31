package com.charo.android.data.datasource.remote.signup

import com.charo.android.data.api.signup.SignUpNickNameCheckViewService
import com.charo.android.data.model.response.signup.ResponseNickNameCheckData


class RemoteSignUpNickNameCheckDataSourceImpl(val service: SignUpNickNameCheckViewService)
    :RemoteSignUpNickNameCheckDataSource{
    override suspend fun nickNameCheck(nickname: String): ResponseNickNameCheckData = service.nickNameCheck(nickname)
}