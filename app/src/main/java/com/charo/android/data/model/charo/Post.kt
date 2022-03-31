package com.charo.android.data.model.charo
data class Post(
    val drive: MutableList<Drive?>,
    var lastCount: Int,
    var lastId: Int
)