package com.charo.android.data.model.request

import com.google.gson.annotations.SerializedName

// νμΈμλ§
data class RequestThemeViewData(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("value")
    val value: String
)


