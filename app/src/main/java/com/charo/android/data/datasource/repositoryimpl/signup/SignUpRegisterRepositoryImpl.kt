package com.charo.android.data.datasource.repositoryimpl.signup

import com.example.charo_android.data.mapper.SignUpMapper
import com.example.charo_android.data.model.request.signup.RequestSignUpGoogleData
import com.example.charo_android.data.model.request.signup.RequestSignUpKaKaoData
import com.example.charo_android.data.model.response.signup.ResponseRegisterData
import com.example.charo_android.data.datasource.remote.signup.RemoteSignUpRegisterDataSource
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SignUpRegisterRepositoryImpl(private val dataSource: RemoteSignUpRegisterDataSource) :
    SignUpRegisterRepository {
    override suspend fun signUpRegister(
        image: MultipartBody.Part,
        userEmail: RequestBody,
        password: RequestBody,
        nickname: RequestBody,
        pushAgree: RequestBody,
        emailAgree: RequestBody
    ): ResponseRegisterData {
        return dataSource.signUpRegister(
            image,
            userEmail,
            password,
            nickname,
            pushAgree,
            emailAgree
        )
    }

    override suspend fun signUpGoogleRegister(requestSignUpSocialData: RequestSignUpGoogleData): StatusCode {
        return SignUpMapper.mapperToSocialSignUp(
            dataSource.signUpGoogleRegister(
                requestSignUpSocialData
            )
        )
    }

    override suspend fun signUpKaKaoRegister(requestSignUpKaKaoData: RequestSignUpKaKaoData): StatusCode {
        return SignUpMapper.mapperToSocialSignUp(
            dataSource.signUpKaKaoRegister(requestSignUpKaKaoData)
        )
    }
}