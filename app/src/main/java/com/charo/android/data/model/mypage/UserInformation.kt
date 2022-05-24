package com.charo.android.data.model.mypage

data class UserInformation(
    val nickname: String,
    val profileImage: String,
    val following: Int,
    var follower: Int
)
