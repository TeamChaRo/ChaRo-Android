package com.example.charo_android.domain.repository.signup

import com.example.charo_android.data.model.request.signup.RequestSignUpSocialData
import com.example.charo_android.data.model.response.signup.ResponseRegisterData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.model.signup.SocialSignUp
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SignUpRegisterRepository {
    suspend fun signUpRegister(
        image: MultipartBody.Part,
        userEmail: RequestBody,
        password: RequestBody,
        nickname: RequestBody,
        pushAgree: RequestBody,
        emailAgree: RequestBody
    ) : ResponseRegisterData

    suspend fun signUpGoogleRegister(requestSignUpSocialData: RequestSignUpSocialData)
        : StatusCode
}