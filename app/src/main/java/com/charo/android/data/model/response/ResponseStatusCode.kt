package com.charo.android.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseStatusCode(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("msg")
    val msg : String
)
