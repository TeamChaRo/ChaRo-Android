package com.charo.android.data.model.mypage

data class ResponseFollow(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val isFollow: Boolean
    )

    fun toBoolean(): Boolean {
        return this.data.isFollow
    }
}
