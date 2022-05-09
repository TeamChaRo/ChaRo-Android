package com.charo.android.data.datasource.remote.signin

import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.data.model.response.ResponseDefaultData
import com.charo.android.data.model.response.signin.ResponseSignInData


interface RemoteEmailSignInDataSource {
    suspend fun postSignIn(requestSignInData: RequestSignInData) : ResponseSignInData

    suspend fun getPasswordSearch(userEmail : String) : ResponseDefaultData
}