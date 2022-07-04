package com.charo.android.data.model.response.follow

import com.google.gson.annotations.SerializedName

data class ResponsePostFollowData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("isFollow")
        val isFollow: Boolean
    )
}
