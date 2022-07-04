package com.charo.android.data.model.response.setting

import com.google.gson.annotations.SerializedName

data class ResponseProfileUpdateData(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("msg")
    val msg : String,
)
