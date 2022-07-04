package com.charo.android.data.model.request.home

import com.google.gson.annotations.SerializedName

data class RequestHomeLikeData(
    @SerializedName("userEmail")
    val userEmail : String,
    @SerializedName("postId")
    val postId : Int
)