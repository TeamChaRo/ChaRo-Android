package com.charo.android.data.model.charo

data class UserInformation(
    val follower: Int,
    val following: Int,
    val nickname: String,
    val profileImage: String
)