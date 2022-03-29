package com.charo.android.domain.usecase.signin

import com.example.charo_android.data.mapper.SignInMapper
import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.domain.model.signin.SocialLoginData
import com.example.charo_android.domain.repository.signin.KakaoSignInRepository

class GetRemoteSocialLoginData(private val repository : KakaoSignInRepository) {

    suspend fun execute(requestSocialData: RequestSocialData) : SocialLoginData {
        return with(SignInMapper){
            mapperToSocialLoginData(repository.kakaoLogin(requestSocialData))
        }
    }
}