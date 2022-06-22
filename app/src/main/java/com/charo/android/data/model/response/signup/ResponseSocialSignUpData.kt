package com.charo.android.data.model.response.signup

// 확인요망
data class ResponseSocialSignUpData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val email: String,
        val nickname: String,
        val profileImage: String
    )
}