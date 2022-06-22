package com.charo.android.data.mapper

import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.data.model.response.ResponseDefaultData
import com.charo.android.data.model.response.signin.ResponseSignInData
import com.charo.android.data.model.response.signin.ResponseSocialData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.signin.EmailSignInData
import com.charo.android.domain.model.signin.SocialLoginData
import com.charo.android.domain.model.signin.SocialUserEmail


object SignInMapper {
    // 확인요망
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

    fun mapperToEmailSignInData(responseSignInData: ResponseSignInData) : EmailSignInData {
        return EmailSignInData(
            success = responseSignInData.success,
            data = EmailSignInData.EmailSignIn(
                userEmail = responseSignInData.data.userEmail,
                nickname = responseSignInData.data.nickname,
                profileImage = responseSignInData.data.profileImage,
                isSocial = responseSignInData.data.isSocial
            )

        )

        }

    //비밀번호찾기
    fun mapperToSearchPassword(responseDefaultData: ResponseDefaultData) : StatusCode{
        return StatusCode(
            success = responseDefaultData.success
        )

    }
    }
