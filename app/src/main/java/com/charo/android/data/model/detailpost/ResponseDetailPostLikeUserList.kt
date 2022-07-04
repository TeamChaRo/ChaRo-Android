package com.charo.android.data.model.detailpost

import com.google.gson.annotations.SerializedName

data class ResponseDetailPostLikeUserList(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: List<Data>
) {
    data class Data(
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("userEmail")
        val userEmail: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("is_follow")
        val isFollow: Boolean
    )
}
