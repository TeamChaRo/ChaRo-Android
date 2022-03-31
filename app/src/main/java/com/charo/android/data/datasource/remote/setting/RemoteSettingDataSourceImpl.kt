package com.charo.android.data.datasource.remote.setting



import com.charo.android.data.api.setting.SettingViewService
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.setting.ResponseProfileUpdateData
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteSettingDataSourceImpl(private val service: SettingViewService)
    : RemoteSettingDataSource{
    override suspend fun profileChange(
        userEmail: String,
        image: MultipartBody.Part,
        originImage: RequestBody,
        newNickname: RequestBody
    ): ResponseProfileUpdateData {
        return service.profileChange(userEmail, image, originImage, newNickname)
    }

    override suspend fun nickNameChange(
        userEmail: String,
        profileImage: RequestBody,
        newNickname: RequestBody
    ): ResponseProfileUpdateData {
        return service.nickNameChange(userEmail,profileImage, newNickname)
    }

    override suspend fun withdrawalUser(userEmail: String) : ResponseStatusCode {
        return service.withdrawalUser(userEmail)
    }

    override suspend fun originPasswordCheck(
        userEmail: String,
        password: String
    ): ResponseStatusCode {
        return service.originPasswordCheck(userEmail, password)
    }

    override suspend fun newPasswordRegister(
        userEmail: String,
        newPassword: String
    ): ResponseStatusCode {
        return service.newPasswordRegister(userEmail, newPassword)
    }
}

