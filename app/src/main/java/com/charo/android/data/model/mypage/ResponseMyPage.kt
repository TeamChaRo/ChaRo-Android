package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ResponseMyPage(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("userInformation")
        val userInformation: UserInformation,
        @SerializedName("writtenPost")
        val writtenPost: PostInfo,
        @SerializedName("savedPost")
        val savedPost: PostInfo
    )
}
