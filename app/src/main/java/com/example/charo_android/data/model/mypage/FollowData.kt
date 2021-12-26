package com.example.charo_android.data.model.mypage

data class FollowData(
    val follower: List<User>,
    val following: List<User>
)