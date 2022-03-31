package com.charo.android.data.datasource.remote.signin

import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.data.model.response.signin.ResponseSocialData


interface RemoteKakaoSignInDataSource {
    suspend fun kakaoLogin(requestSocialData: RequestSocialData) : ResponseSocialData
}