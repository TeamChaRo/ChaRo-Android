package com.charo.android.data.model.request.follow

import com.google.gson.annotations.SerializedName

data class RequestPostFollowData(
    @SerializedName("follower")
    val follower: String,
    @SerializedName("followed")
    val followed: String
)