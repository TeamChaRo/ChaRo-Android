package com.charo.android.domain.repository.signin

import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData
import com.example.charo_android.domain.model.signin.SocialUserEmail

interface KakaoSignInRepository {
    suspend fun kakaoLogin(requestSocialData: RequestSocialData) : ResponseSocialData
}