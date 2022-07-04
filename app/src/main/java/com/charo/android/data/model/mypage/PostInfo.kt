package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class PostInfo(
    @SerializedName("lastId")
    val lastId: Int,
    @SerializedName("lastCount")
    val lastCount: Int,
    @SerializedName("drive")
    val drive: MutableList<Post>
)
