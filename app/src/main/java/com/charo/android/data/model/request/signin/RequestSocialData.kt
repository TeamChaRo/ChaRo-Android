package com.charo.android.data.model.request.signin

import com.google.gson.annotations.SerializedName

data class RequestSocialData(
    @SerializedName("userEmail")
    val userEmail: String
    )