package com.charo.android.data.datasource.local.setting

import com.example.charo_android.domain.model.setting.SettingNoticeData

interface LocalSettingNoticeDataSource {
    fun fetchData() : MutableList<SettingNoticeData>
}