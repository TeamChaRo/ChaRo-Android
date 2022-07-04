package com.charo.android.data.model.request

import com.google.gson.annotations.SerializedName

// 확인요망
data class RequestThemeViewData(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("value")
    val value: String
)


