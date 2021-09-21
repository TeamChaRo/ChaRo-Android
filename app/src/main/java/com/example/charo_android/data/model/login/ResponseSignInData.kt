package com.example.charo_android.data.model.login

data class ResponseSignInData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val email: String,
        val nickname: String,
        val profileImage: String,
        val token: String,
    )
}