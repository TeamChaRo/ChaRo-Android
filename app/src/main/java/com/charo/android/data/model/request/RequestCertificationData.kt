package com.charo.android.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestCertificationData(
    @SerializedName("userEmail")
    val userEmail: String
    )
