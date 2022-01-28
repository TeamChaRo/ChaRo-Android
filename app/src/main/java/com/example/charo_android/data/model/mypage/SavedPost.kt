package com.example.charo_android.data.model.mypage

data class SavedPost(
    val lastId: Int,
    val lastCount: Int,
    val drive: List<Post>
)