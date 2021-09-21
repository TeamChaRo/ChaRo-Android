package com.example.charo_android.data.model.mypage


data class Post(
    val drive: MutableList<Drive?>,
    var lastCount: Int,
    var lastId: Int
)