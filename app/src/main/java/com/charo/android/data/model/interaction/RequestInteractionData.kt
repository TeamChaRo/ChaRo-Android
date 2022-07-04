package com.charo.android.data.model.interaction

import com.google.gson.annotations.SerializedName

data class RequestInteractionData(
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("postId")
    val postId: Int
)
