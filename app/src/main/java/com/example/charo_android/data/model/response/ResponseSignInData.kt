package com.example.charo_android.data.model.response

data class ResponseSignInData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val nickname: String,
        val token: String,
        val email: String
    )
}