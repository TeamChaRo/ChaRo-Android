package com.charo.android.data.datasource.local.setting

import com.charo.android.domain.model.setting.SettingNoticeData


interface LocalSettingNoticeDataSource {
    fun fetchData() : MutableList<SettingNoticeData>
}