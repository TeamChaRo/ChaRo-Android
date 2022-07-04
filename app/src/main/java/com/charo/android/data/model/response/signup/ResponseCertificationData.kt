package com.charo.android.data.model.response.signup

import com.google.gson.annotations.SerializedName

data class ResponseCertificationData(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: String)