package com.example.charo_android.data.repository.repositoryimpl.signin

import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData
import com.example.charo_android.data.repository.remote.signin.RemoteKakaoSignInDataSource
import com.example.charo_android.domain.repository.signin.KakaoSignInRepository

class KakaoSignInRepositoryImpl(private val dataSource: RemoteKakaoSignInDataSource) :
    KakaoSignInRepository {
    override suspend fun kakaoLogin(requestSocialData: RequestSocialData): ResponseSocialData
    {
        return dataSource.kakaoLogin(requestSocialData)
    }
}