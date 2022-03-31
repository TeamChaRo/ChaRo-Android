package com.charo.android.domain.repository.setting


import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.setting.ProfileChangeData
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SettingRepository {
    suspend fun profileChange(
        userEmail : String,
        image : MultipartBody.Part,
        originImage : RequestBody,
        newNickname : RequestBody
    ) : ProfileChangeData

    suspend fun nickNameChange(
        userEmail: String,
        profileImage : RequestBody,
        newNickname: RequestBody
    ) : ProfileChangeData

    suspend fun withdrawalUser(
        userEmail : String
    ) : StatusCode

    suspend fun originPasswordCheck(
        userEmail : String,
        password : String
    ) : StatusCode

    suspend fun newPasswordRegister(
        userEmail: String,
        newPassword: String
    ) : StatusCode
}