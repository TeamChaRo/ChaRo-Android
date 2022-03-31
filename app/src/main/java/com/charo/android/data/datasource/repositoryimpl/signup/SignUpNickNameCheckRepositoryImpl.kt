package com.charo.android.data.datasource.repositoryimpl.signup

import com.charo.android.data.datasource.remote.signup.RemoteSignUpNickNameCheckDataSource
import com.charo.android.data.model.response.signup.ResponseNickNameCheckData
import com.charo.android.domain.repository.signup.SignUpNickNameCheckRepository


class SignUpNickNameCheckRepositoryImpl(val remoteDataSource: RemoteSignUpNickNameCheckDataSource)
    : SignUpNickNameCheckRepository {
    override suspend fun nickNameCheck(nickname: String): ResponseNickNameCheckData {
        return remoteDataSource.nickNameCheck(nickname)
    }
}