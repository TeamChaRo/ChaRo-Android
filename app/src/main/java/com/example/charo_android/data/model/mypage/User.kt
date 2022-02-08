package com.example.charo_android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class User(
    val nickname: String,
    val userEmail: String,
    val image: String,
    @SerializedName("is_follow")
    val isFollow: Boolean
)
