package com.charo.android.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class RequestSignUpGoogleData(
    @SerializedName("userEmail")
    val userEmail : String,
    @SerializedName("profileImage")
    val profileImage : String,
    @SerializedName("pushAgree")
    val pushAgree : Boolean,
    @SerializedName("emailAgree")
    val emailAgree : Boolean
)
