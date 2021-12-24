package com.example.charo_android.data.repository.local.setting

import com.example.charo_android.domain.model.setting.SettingNoticeData

interface LocalSettingNoticeDataSource {
    fun fetchData() : MutableList<SettingNoticeData>
}