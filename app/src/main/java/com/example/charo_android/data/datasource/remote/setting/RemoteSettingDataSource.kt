package com.example.charo_android.data.datasource.remote.setting

import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.setting.ResponseProfileUpdateData
import okhttp3.MultipartBody
import okhttp3.RequestBody

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


    suspend fun withdrawalUser(
        userEmail: String
    ) : ResponseStatusCode

    suspend fun originPasswordCheck(
        userEmail : String,
        password : String
    ) : ResponseStatusCode

    suspend fun newPasswordRegister(
        userEmail: String,
        newPassword: String
    ) : ResponseStatusCode
}