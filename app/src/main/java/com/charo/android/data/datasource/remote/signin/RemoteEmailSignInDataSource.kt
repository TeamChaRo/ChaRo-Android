package com.charo.android.data.datasource.remote.signin

import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.data.model.response.signin.ResponseSignInData

interface RemoteEmailSignInDataSource {
    suspend fun postSignIn(requestSignInData: RequestSignInData ) : ResponseSignInData
}