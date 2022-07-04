package com.charo.android.data.model.response.signin

import com.google.gson.annotations.SerializedName

data class ResponseSocialData(
    @SerializedName("data")
    val data : Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("isSocial")
        val isSocial: Boolean,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("profileImage")
        val profileImage: String
    )
}