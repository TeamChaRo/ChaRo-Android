package com.charo.android.data.model.request.alarm

import com.google.gson.annotations.SerializedName

data class RequestFcmData(
    @SerializedName("token")
    val token: String,
    @SerializedName("userEmail")
    val userEmail: String
)
