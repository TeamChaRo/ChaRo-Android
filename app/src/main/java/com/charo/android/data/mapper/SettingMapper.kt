package com.charo.android.data.mapper

import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.setting.ResponseProfileUpdateData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.setting.ProfileChangeData


object SettingMapper {

    fun mapperToProfileChange(responseProfileUpdateData: ResponseProfileUpdateData) : ProfileChangeData {
        return ProfileChangeData(
            success = responseProfileUpdateData.success,
            msg = responseProfileUpdateData.msg
        )
    }

    fun mapperToStatusCode(responseStatusCode: ResponseStatusCode) : StatusCode {
        return StatusCode(
            success = responseStatusCode.success
        )
    }
}