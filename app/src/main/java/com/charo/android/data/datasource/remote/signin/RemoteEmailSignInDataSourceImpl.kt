package com.charo.android.data.datasource.remote.signin

import com.charo.android.data.api.signin.SignInViewService
import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.data.model.response.signin.ResponseSignInData


class RemoteEmailSignInDataSourceImpl(private val service: SignInViewService)
    : RemoteEmailSignInDataSource{
    override suspend fun postSignIn(requestSignInData: RequestSignInData): ResponseSignInData {
        return service.postSignIn(requestSignInData)
    }
}