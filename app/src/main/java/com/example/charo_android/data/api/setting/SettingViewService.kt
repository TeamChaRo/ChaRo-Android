package com.example.charo_android.data.api.setting

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SettingViewService {
    @Multipart
    @POST("/user/{userEmail}")
    fun postSetting(
        @Path("userEmail") userEmail : String,
        @Part("image") image : MultipartBody.Part ?= null,
        @Part("originImage") originImage : RequestBody,
        @Part("newNickname") newNickname : RequestBody
    ){

    }
}