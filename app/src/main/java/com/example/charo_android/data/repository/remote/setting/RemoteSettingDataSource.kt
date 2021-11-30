package com.example.charo_android.data.repository.remote.setting

import com.example.charo_android.data.api.setting.SettingViewService
import com.example.charo_android.data.model.response.setting.ResponseProfileUpdateData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import retrofit2.http.Path

interface RemoteSettingDataSource {
    suspend fun profileChange(
        userEmail : String,
        image : MultipartBody.Part,
        originImage : RequestBody,
        newNickname : RequestBody
    ) : ResponseProfileUpdateData

    suspend fun nickNameChange(
        userEmail: String,
        profileImage : RequestBody,
        newNickname: RequestBody
    ) : ResponseProfileUpdateData
}