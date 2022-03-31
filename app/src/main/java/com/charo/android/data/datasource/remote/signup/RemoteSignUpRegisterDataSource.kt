package com.charo.android.data.datasource.remote.signup

import com.charo.android.data.model.request.signup.RequestSignUpGoogleData
import com.charo.android.data.model.request.signup.RequestSignUpKaKaoData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.signup.ResponseRegisterData
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

    suspend fun signUpGoogleRegister(
        requestSignUpSocialData : RequestSignUpGoogleData
    ) : ResponseStatusCode

    suspend fun signUpKaKaoRegister(
        requestSignUpKaKaoData: RequestSignUpKaKaoData
    ) : ResponseStatusCode
}