package com.example.charo_android.data.repository.setting

import com.example.charo_android.data.mapper.SettingMapper
import com.example.charo_android.data.repository.remote.setting.RemoteSettingDataSource
import com.example.charo_android.domain.model.setting.ProfileChangeData
import com.example.charo_android.domain.repository.SettingRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SettingRepositoryImpl(private val dataSource: RemoteSettingDataSource)
    : SettingRepository{
    override suspend fun profileChange(
        userEmail: String,
        image: MultipartBody.Part,
        originImage: RequestBody,
        newNickname: RequestBody
    ): ProfileChangeData {
        return SettingMapper.mapperToProfileChange(dataSource.profileChange(userEmail, image, originImage, newNickname))
    }

    override suspend fun nickNameChange(
        userEmail: String,
        profileImage: RequestBody,
        newNickname: RequestBody
    ): ProfileChangeData {
        return SettingMapper.mapperToProfileChange(dataSource.nickNameChange(userEmail, profileImage, newNickname))
    }
}