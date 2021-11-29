package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData
import com.example.charo_android.domain.model.signin.SocialLoginData
import com.example.charo_android.domain.model.signin.SocialUserEmail

object SignInMapper {
    fun mapperToSocialUserEmail(requestSocialData: RequestSocialData) : SocialUserEmail {
        return SocialUserEmail(
            userEmail = requestSocialData.userEmail
        )
    }

    fun mapperToSocialLoginData(responseKakaoData: ResponseSocialData) : SocialLoginData {
        return SocialLoginData(
            success = responseKakaoData.success,
            email = responseKakaoData.data.email,
            nickname = responseKakaoData.data.nickname,
            profileImage = responseKakaoData.data.profileImage,
            isSocial = responseKakaoData.data.isSocial
        )
    }
}