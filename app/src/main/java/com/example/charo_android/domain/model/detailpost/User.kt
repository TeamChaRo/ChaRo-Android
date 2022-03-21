package com.example.charo_android.domain.model.detailpost

data class User(
    val nickname: String,
    val userEmail: String,
    val image: String,
    var isFollow: Boolean
)
