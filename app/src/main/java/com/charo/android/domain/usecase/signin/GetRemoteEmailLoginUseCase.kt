package com.charo.android.domain.usecase.signin

import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.domain.model.signin.EmailSignInData
import com.charo.android.domain.repository.signin.EmailSignInRepository

class GetRemoteEmailLoginUseCase(private val repository : EmailSignInRepository) {
    suspend fun execute(requestSignInData: RequestSignInData) : EmailSignInData {
        return repository.postSignIn(requestSignInData)
    }
}