package com.charo.android.data.model.response.alarm

import com.google.gson.annotations.SerializedName

data class ResponseAlarmListData (
    @SerializedName("data")
    val pushList: ArrayList<PushList>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class PushList(
        @SerializedName("push_id")
        val pushId: Int,
        @SerializedName("push_code")
        val pushCode: Int,
        @SerializedName("is_read")
        val isRead: Int,
        @SerializedName("token")
        val token: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("day")
        val day: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("postId")
        val postId: Int,
        @SerializedName("followed")
        val followed: String,
    )
}