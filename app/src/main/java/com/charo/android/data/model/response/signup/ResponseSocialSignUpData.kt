package com.charo.android.data.model.response.signup

import com.google.gson.annotations.SerializedName

// 확인요망
data class ResponseSocialSignUpData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("profileImage")
        val profileImage: String
    )
}