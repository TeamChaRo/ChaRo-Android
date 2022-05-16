package com.charo.android.data.model.response.alarm

import com.google.gson.annotations.SerializedName

data class ResponseAlarmListData (
    val pushList: PushList,
    val msg: String,
    val success: Boolean
) {
    data class PushList(
        @SerializedName("push_id")
        val pushId: Int,
        @SerializedName("push_code")
        val pushCode: Int,
        @SerializedName("is_read")
        val isRead: Int,
        val token: String,
        val image: String,
        val title: String,
        val body: String,
        val month: String,
        val day: String,
        val type: String,
        val postId: Int,
        val followed: String,
    )
}