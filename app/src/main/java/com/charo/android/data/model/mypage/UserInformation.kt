package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class UserInformation(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("following")
    var following: Int,
    @SerializedName("follower")
    var follower: Int
)
