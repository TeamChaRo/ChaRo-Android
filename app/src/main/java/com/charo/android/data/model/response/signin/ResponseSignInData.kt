package com.charo.android.data.model.response.signin

import com.google.gson.annotations.SerializedName

data class ResponseSignInData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("userEmail")
        val userEmail : String,
        @SerializedName("nickname")
        val nickname : String,
        @SerializedName("profileImage")
        val profileImage : String,
        @SerializedName("isSocial")
        val isSocial : Boolean
    )
}