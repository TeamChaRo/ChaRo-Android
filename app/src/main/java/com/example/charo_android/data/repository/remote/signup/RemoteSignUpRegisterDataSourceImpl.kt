package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.api.signup.SignUpRegisterService
import com.example.charo_android.data.model.response.signup.ResponseRegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteSignUpRegisterDataSourceImpl(private val service: SignUpRegisterService)
    : RemoteSignUpRegisterDataSource{
    override suspend fun signUpRegister(
        image: MultipartBody.Part,
        userEmail: RequestBody,
        password: RequestBody,
        nickname: RequestBody,
        pushAgree: RequestBody,
        emailAgree: RequestBody
    ): ResponseRegisterData = service.signUpRegister(image, userEmail, password, nickname, pushAgree, emailAgree)
}