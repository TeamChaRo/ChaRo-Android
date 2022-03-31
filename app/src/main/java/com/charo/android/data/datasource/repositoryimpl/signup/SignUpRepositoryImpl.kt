package com.charo.android.data.datasource.repositoryimpl.signup

import com.charo.android.data.datasource.remote.signup.RemoteSignUpEmailCheckDataSource
import com.charo.android.data.model.response.signup.ResponseEmailCheckData
import com.charo.android.domain.repository.signup.SignUpRepository


class SignUpRepositoryImpl(private val remoteDataSource: RemoteSignUpEmailCheckDataSource):
    SignUpRepository {
    override suspend fun emailCheck(email: String): ResponseEmailCheckData {
        return remoteDataSource.emailCheck(email)
    }
}