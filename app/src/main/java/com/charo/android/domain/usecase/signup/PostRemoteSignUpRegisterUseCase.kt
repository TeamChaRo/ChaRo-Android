package com.charo.android.domain.usecase.signup


import com.charo.android.data.mapper.SignUpMapper
import com.charo.android.domain.repository.signup.SignUpRegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostRemoteSignUpRegisterUseCase(private val repository : SignUpRegisterRepository) {
    suspend fun execute(
        image: MultipartBody.Part,
        userEmail: RequestBody,
        password: RequestBody,
        nickname: RequestBody,
        pushAgree: RequestBody,
        emailAgree: RequestBody
    ) : Boolean {
        return SignUpMapper.mapperToRegister(
            repository.signUpRegister(image, userEmail, password, nickname, pushAgree, emailAgree)
        )
    }

}