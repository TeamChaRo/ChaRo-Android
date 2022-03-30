package com.charo.android.domain.usecase.setting

import com.charo.android.domain.model.setting.ProfileChangeData
import com.charo.android.domain.repository.setting.SettingRepository
import com.example.charo_android.domain.model.setting.ProfileChangeData
import com.example.charo_android.domain.repository.setting.SettingRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileImageChangeUseCase(private val repository : SettingRepository) {
    suspend fun execute( userEmail: String,
                        image: MultipartBody.Part,
                        originImage: RequestBody,
                        newNickname: RequestBody ) : ProfileChangeData {
        return repository.profileChange(userEmail, image, originImage, newNickname)
    }

}