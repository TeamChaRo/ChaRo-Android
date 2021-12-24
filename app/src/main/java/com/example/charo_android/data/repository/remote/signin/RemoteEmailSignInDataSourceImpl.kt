package com.example.charo_android.data.repository.remote.signin

import com.example.charo_android.data.api.signin.SignInViewService
import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.data.model.response.signin.ResponseSignInData

class RemoteEmailSignInDataSourceImpl(private val service: SignInViewService)
    : RemoteEmailSignInDataSource{
    override suspend fun postSignIn(requestSignInData: RequestSignInData): ResponseSignInData {
        return service.postSignIn(requestSignInData)
    }
}