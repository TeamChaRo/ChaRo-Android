package com.charo.android.data.model.request.signup

import com.google.gson.annotations.SerializedName

data class RequestSignUpKaKaoData(
    @SerializedName("userEmail")
    val userEmail : String,
    @SerializedName("profileImage")
    val profileImage : String,
    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("pushAgree")
    val pushAgree : Boolean,
    @SerializedName("emailAgree")
    val emailAgree : Boolean
)
