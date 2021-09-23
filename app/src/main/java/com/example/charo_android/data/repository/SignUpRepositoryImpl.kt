package com.example.charo_android.data.repository

import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.data.repository.remote.signup.RemoteSignUpEmailCheckDataSource
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.domain.repository.SignUpRepository

class SignUpRepositoryImpl(private val remoteDataSource: RemoteSignUpEmailCheckDataSource): SignUpRepository {
    override suspend fun emailCheck(email: Email): ResponseEmailCheckData {
        return remoteDataSource.emailCheck(email)
    }
}