package com.example.charo_android.data.repository.remote.signin

import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.data.model.response.signin.ResponseSignInData

interface RemoteEmailSignInDataSource {
    suspend fun postSignIn(requestSignInData: RequestSignInData ) : ResponseSignInData
}