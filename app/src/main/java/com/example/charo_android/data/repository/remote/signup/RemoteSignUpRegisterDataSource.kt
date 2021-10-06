package com.example.charo_android.data.repository.remote.signup

import com.example.charo_android.data.model.response.signup.ResponseRegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteSignUpRegisterDataSource{
    suspend fun signUpRegister(
        image: MultipartBody.Part,
        userEmail: RequestBody,
        password:RequestBody,
        nickname : RequestBody,
        pushAgree : RequestBody,
        emailAgree : RequestBody,
    ) : ResponseRegisterData
}