package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ResponseFollow(
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

    fun toBoolean(): Boolean {
        return this.data.isFollow
    }
}
