package com.charo.android.data.model.response.signup

import com.google.gson.annotations.SerializedName

data class ResponseNickNameCheckData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String )
