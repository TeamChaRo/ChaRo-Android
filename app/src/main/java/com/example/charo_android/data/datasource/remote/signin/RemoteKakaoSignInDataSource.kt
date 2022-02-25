package com.example.charo_android.data.datasource.remote.signin

import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData

interface RemoteKakaoSignInDataSource {
    suspend fun kakaoLogin(requestSocialData: RequestSocialData) : ResponseSocialData
}