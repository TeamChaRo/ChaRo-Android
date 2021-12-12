package com.example.charo_android.domain.model.setting

data class SettingNoticeData(
    val title : String,
    val date : String,
    val content : String,
    var view : Boolean = false
)