package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class User(
    val nickname: String,
    val userEmail: String,
    val image: String,
    @SerializedName("is_follow")
    var isFollow: Boolean
)
