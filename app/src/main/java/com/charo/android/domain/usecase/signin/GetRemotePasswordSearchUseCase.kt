package com.charo.android.domain.usecase.signin

import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.signin.EmailSignInRepository

class GetRemotePasswordSearchUseCase(private val repository : EmailSignInRepository) {

    suspend operator fun invoke(userEmail  :String) : StatusCode{
        return repository.getPasswordSearch(userEmail)
    }
}