package com.charo.android.domain.usecase.signin

import com.charo.android.data.mapper.SignInMapper
import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.domain.model.signin.SocialLoginData
import com.charo.android.domain.repository.signin.KakaoSignInRepository


class GetRemoteSocialLoginData(private val repository : KakaoSignInRepository) {

    suspend fun execute(requestSocialData: RequestSocialData) : SocialLoginData {
        return with(SignInMapper){
            mapperToSocialLoginData(repository.kakaoLogin(requestSocialData))
        }
    }
}