package com.charo.android.data.model.write

import com.google.gson.annotations.SerializedName

data class ResponseEditData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String
)
