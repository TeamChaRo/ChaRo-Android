package com.example.charo_android.data.repository.repositoryimpl.signin

import com.example.charo_android.data.mapper.SignInMapper
import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.data.repository.remote.signin.RemoteEmailSignInDataSource
import com.example.charo_android.domain.model.signin.EmailSignInData
import com.example.charo_android.domain.repository.signin.EmailSignInRepository

class EmailSignInRepositoryImpl(private val dataSource: RemoteEmailSignInDataSource)
    : EmailSignInRepository{
    override suspend fun postSignIn(requestSignInData: RequestSignInData): EmailSignInData {
        return SignInMapper.mapperToEmailSignInData(dataSource.postSignIn(requestSignInData))
    }
}