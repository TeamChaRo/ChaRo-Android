package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.response.setting.ResponseProfileUpdateData
import com.example.charo_android.domain.model.setting.ProfileChangeData

object SettingMapper {

    fun mapperToProfileChange(responseProfileUpdateData: ResponseProfileUpdateData) : ProfileChangeData{
        return ProfileChangeData(
            success = responseProfileUpdateData.success,
            msg = responseProfileUpdateData.msg
        )

    }
}