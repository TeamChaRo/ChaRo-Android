package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_follow")
    var isFollow: Boolean
)
