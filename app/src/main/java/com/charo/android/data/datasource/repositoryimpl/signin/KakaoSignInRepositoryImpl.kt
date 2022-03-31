package com.charo.android.data.datasource.repositoryimpl.signin

import com.charo.android.data.datasource.remote.signin.RemoteKakaoSignInDataSource
import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.data.model.response.signin.ResponseSocialData
import com.charo.android.domain.repository.signin.KakaoSignInRepository


class KakaoSignInRepositoryImpl(private val dataSource: RemoteKakaoSignInDataSource) :
    KakaoSignInRepository {
    override suspend fun kakaoLogin(requestSocialData: RequestSocialData): ResponseSocialData
    {
        return dataSource.kakaoLogin(requestSocialData)
    }
}