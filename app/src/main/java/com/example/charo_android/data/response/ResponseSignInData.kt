package com.example.charo_android.data.response

data class ResponseSignInData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val nickname: String,
        val token: String,
        val userId: String
    )
}