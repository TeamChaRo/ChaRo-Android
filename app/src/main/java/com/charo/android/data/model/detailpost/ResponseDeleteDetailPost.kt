package com.charo.android.data.model.detailpost

import com.google.gson.annotations.SerializedName

data class ResponseDeleteDetailPost(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    fun toBoolean(): Boolean {
        return success
    }
}
