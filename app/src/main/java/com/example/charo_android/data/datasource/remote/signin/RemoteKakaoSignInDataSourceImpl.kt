package com.example.charo_android.data.datasource.remote.signin

import com.example.charo_android.data.api.signin.KakaoSignInService
import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData

class RemoteKakaoSignInDataSourceImpl(private val service : KakaoSignInService)
    : RemoteKakaoSignInDataSource{
    override suspend fun kakaoLogin(requestSocialData: RequestSocialData): ResponseSocialData
    = service.kakaoLogin(requestSocialData)
}