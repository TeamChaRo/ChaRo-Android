package com.charo.android.data.datasource.repositoryimpl.setting

import com.charo.android.data.datasource.remote.setting.RemoteSettingDataSource
import com.charo.android.data.mapper.SettingMapper
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.setting.ProfileChangeData
import com.charo.android.domain.repository.setting.SettingRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SettingRepositoryImpl(private val dataSource: RemoteSettingDataSource)
    : SettingRepository {
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

    override suspend fun withdrawalUser(userEmail: String) : StatusCode {
        return SettingMapper.mapperToStatusCode(dataSource.withdrawalUser(userEmail))
    }

    override suspend fun originPasswordCheck(userEmail: String, password: String): StatusCode {
        return SettingMapper.mapperToStatusCode(dataSource.originPasswordCheck(userEmail, password))
    }

    override suspend fun newPasswordRegister(userEmail: String, newPassword: String): StatusCode {
        return SettingMapper.mapperToStatusCode(dataSource.newPasswordRegister(userEmail, newPassword))
    }
}