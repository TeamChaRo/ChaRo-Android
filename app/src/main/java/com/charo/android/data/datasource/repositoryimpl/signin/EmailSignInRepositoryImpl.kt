package com.charo.android.data.datasource.repositoryimpl.signin

import com.charo.android.data.datasource.remote.signin.RemoteEmailSignInDataSource
import com.charo.android.data.mapper.SignInMapper
import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.domain.model.signin.EmailSignInData
import com.charo.android.domain.repository.signin.EmailSignInRepository


class EmailSignInRepositoryImpl(private val dataSource: RemoteEmailSignInDataSource)
    : EmailSignInRepository {
    override suspend fun postSignIn(requestSignInData: RequestSignInData): EmailSignInData {
        return SignInMapper.mapperToEmailSignInData(dataSource.postSignIn(requestSignInData))
    }
}