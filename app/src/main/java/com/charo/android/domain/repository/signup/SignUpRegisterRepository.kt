package com.charo.android.domain.repository.signup


import com.charo.android.data.model.request.signup.RequestSignUpGoogleData
import com.charo.android.data.model.request.signup.RequestSignUpKaKaoData
import com.charo.android.data.model.response.signup.ResponseRegisterData
import com.charo.android.domain.model.StatusCode
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

    suspend fun signUpGoogleRegister(requestSignUpSocialData: RequestSignUpGoogleData)
        : StatusCode


    suspend fun signUpKaKaoRegister(requestSignUpKaKaoData: RequestSignUpKaKaoData)
        : StatusCode
}