package com.example.charo_android.data.api.login

import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.data.model.response.signin.ResponseSocialData
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoSignInService {
    @POST("/user/socialLogin")
    suspend fun kakaoLogin(
        @Body requestKakaoData : RequestSocialData
    ) : ResponseSocialData
}