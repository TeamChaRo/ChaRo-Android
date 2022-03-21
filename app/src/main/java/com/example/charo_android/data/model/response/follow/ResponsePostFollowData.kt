package com.example.charo_android.data.model.response.follow

data class ResponsePostFollowData(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val isFollow: Boolean
    )
}
