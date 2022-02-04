package com.example.charo_android.data.model.response.alarm

data class ResponseAlarmListData (
    val pushList: PushList,
    val msg: String,
    val success: Boolean
) {
    data class PushList(
        val pushId: Int,
        val pushCode: Int,
        val isRead: Int,
        val token: String,
        val image: String,
        val title: String,
        val body: String,
        val month: String,
        val day: String,
    )
}