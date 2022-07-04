package com.charo.android.data.model.response.alarm

import com.google.gson.annotations.SerializedName

data class ResponseAlarmDeleteData (
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
}