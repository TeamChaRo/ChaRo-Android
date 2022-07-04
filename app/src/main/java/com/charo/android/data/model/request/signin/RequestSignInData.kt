package com.charo.android.data.model.request.signin

import com.google.gson.annotations.SerializedName

data class RequestSignInData(
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("password")
    val password: String
)