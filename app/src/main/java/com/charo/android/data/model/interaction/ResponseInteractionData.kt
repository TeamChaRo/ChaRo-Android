package com.charo.android.data.model.interaction

import com.google.gson.annotations.SerializedName

data class ResponseInteractionData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String
)
