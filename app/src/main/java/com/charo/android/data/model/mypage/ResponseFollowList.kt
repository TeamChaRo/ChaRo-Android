package com.charo.android.data.model.mypage

data class ResponseFollowList(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val follower: List<User>,
        val following: List<User>
    )
}
