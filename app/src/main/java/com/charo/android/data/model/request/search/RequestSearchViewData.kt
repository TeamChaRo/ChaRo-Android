package com.charo.android.data.model.request.search

import com.google.gson.annotations.SerializedName

data class RequestSearchViewData(
    @SerializedName("region")
    val region : String,
    @SerializedName("theme")
    val theme : String,
    @SerializedName("warning")
    val warning : String,
    @SerializedName("userEmail")
    val userEmail : String
)
