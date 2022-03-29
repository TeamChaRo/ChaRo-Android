package com.charo.android.data.model.response.signin

data class ResponseSignInData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val userEmail : String,
        val nickname : String,
        val profileImage : String,
        val isSocial : Boolean
    )
}