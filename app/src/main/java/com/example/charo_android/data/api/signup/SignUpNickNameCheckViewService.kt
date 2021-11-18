package com.example.charo_android.data.api.signup

import com.example.charo_android.data.model.response.signup.ResponseNickNameCheckData
import retrofit2.http.GET
import retrofit2.http.Path

interface SignUpNickNameCheckViewService {
    @GET("user/check/nickname/{nickname}")
    suspend fun nickNameCheck(
        @Path("nickname") nickname: String
    ) : ResponseNickNameCheckData
}