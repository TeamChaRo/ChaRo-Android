package com.charo.android.data.model.detailpost

data class ResponseDeleteDetailPost(
    val success: Boolean,
    val msg: String
) {
    fun toBoolean(): Boolean {
        return success
    }
}
