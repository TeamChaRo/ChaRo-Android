package com.charo.android.domain.usecase.setting

import com.charo.android.domain.model.setting.ProfileChangeData
import com.charo.android.domain.repository.setting.SettingRepository
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