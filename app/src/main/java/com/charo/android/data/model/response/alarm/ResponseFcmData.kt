package com.charo.android.data.model.response.alarm

import com.google.gson.annotations.SerializedName

data class ResponseFcmData (
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
}