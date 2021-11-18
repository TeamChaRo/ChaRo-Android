package com.example.charo_android.data.model.response.signin

data class ResponseSocialData(
    val data : Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val email: String,
        val isSocial: Boolean,
        val nickname: String,
        val profileImage: String
    )
}