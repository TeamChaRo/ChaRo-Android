package com.example.charo_android.domain.usecase.signin

import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.domain.model.signin.EmailSignInData
import com.example.charo_android.domain.repository.signin.EmailSignInRepository

class GetRemoteEmailLoginUseCase(private val repository : EmailSignInRepository) {
    suspend fun execute(requestSignInData: RequestSignInData) : EmailSignInData{
        return repository.postSignIn(requestSignInData)
    }
}