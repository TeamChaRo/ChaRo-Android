package com.charo.android.data.datasource.remote.signin

import com.charo.android.data.api.signin.KakaoSignInService
import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.data.model.response.signin.ResponseSocialData


class RemoteKakaoSignInDataSourceImpl(private val service : KakaoSignInService)
    : RemoteKakaoSignInDataSource{
    override suspend fun kakaoLogin(requestSocialData: RequestSocialData): ResponseSocialData
    = service.kakaoLogin(requestSocialData)
}