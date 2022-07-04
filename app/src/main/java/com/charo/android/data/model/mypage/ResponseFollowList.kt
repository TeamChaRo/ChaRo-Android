package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ResponseFollowList(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("follower")
        val follower: List<User>,
        @SerializedName("following")
        val following: List<User>
    )
}
