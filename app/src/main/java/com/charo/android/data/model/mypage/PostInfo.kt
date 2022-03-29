package com.charo.android.data.model.mypage

data class PostInfo(
    val lastId: Int,
    val lastCount: Int,
    val drive: MutableList<Post>
)
