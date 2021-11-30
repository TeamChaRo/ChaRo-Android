package com.example.charo_android.domain.usecase.setting

import com.example.charo_android.domain.model.setting.ProfileChangeData
import com.example.charo_android.domain.repository.SettingRepository
import okhttp3.RequestBody

class ProfileNickNameChangeUseCase(private val repository : SettingRepository) {
    suspend fun execute(
        userEmail: String,
        profileImage: RequestBody,
        newNickname: RequestBody
    ) : ProfileChangeData {
        return repository.nickNameChange(userEmail, profileImage, newNickname)
    }
}