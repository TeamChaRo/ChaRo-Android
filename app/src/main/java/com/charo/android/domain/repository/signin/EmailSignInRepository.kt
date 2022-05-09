package com.charo.android.domain.repository.signin

import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.signin.EmailSignInData


interface EmailSignInRepository {
    suspend fun postSignIn(requestSignInData: RequestSignInData) : EmailSignInData

    suspend fun getPasswordSearch(userEmail : String) : StatusCode
}