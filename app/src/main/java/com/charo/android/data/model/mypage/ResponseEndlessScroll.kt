package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ResponseEndlessScroll(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: PostInfo
)
