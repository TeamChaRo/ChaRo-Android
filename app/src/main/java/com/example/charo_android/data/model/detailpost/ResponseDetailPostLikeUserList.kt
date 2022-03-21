package com.example.charo_android.data.model.detailpost

import com.google.gson.annotations.SerializedName

data class ResponseDetailPostLikeUserList(
    val success: Boolean,
    val msg: String,
    val data: List<Data>
) {
    data class Data(
        val nickname: String,
        val userEmail: String,
        val image: String,
        @SerializedName("is_follow")
        val isFollow: Boolean
    )
}
