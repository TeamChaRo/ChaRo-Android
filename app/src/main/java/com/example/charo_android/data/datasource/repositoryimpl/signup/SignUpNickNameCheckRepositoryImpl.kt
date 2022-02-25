package com.example.charo_android.data.datasource.repositoryimpl.signup

import com.example.charo_android.data.model.response.signup.ResponseNickNameCheckData
import com.example.charo_android.data.datasource.remote.signup.RemoteSignUpNickNameCheckDataSource
import com.example.charo_android.domain.repository.signup.SignUpNickNameCheckRepository

class SignUpNickNameCheckRepositoryImpl(val remoteDataSource: RemoteSignUpNickNameCheckDataSource)
    :SignUpNickNameCheckRepository{
    override suspend fun nickNameCheck(nickname: String): ResponseNickNameCheckData {
        return remoteDataSource.nickNameCheck(nickname)
    }
}