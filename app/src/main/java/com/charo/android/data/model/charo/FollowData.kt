package com.charo.android.data.model.charo

data class FollowData(
    val follower: List<User>,
    val following: List<User>
)