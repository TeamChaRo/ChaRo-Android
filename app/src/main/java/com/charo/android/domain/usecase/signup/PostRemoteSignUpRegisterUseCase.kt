package com.charo.android.domain.usecase.signup

import android.graphics.Bitmap
import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.data.model.response.signup.ResponseRegisterData
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink

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