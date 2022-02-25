package com.example.charo_android.data.datasource.repositoryimpl.signup

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.data.datasource.remote.signup.RemoteSignUpEmailCheckDataSource
import com.example.charo_android.domain.repository.signup.SignUpRepository

class SignUpRepositoryImpl(private val remoteDataSource: RemoteSignUpEmailCheckDataSource):
    SignUpRepository {
    override suspend fun emailCheck(email: String): ResponseEmailCheckData {
        return remoteDataSource.emailCheck(email)
    }
}