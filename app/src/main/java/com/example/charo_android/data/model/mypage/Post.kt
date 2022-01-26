package com.example.charo_android.data.model.mypage

data class Post(
    val postId: Int,
    val title: String,
    val image: String,
    val region: String,
    val theme: String,
    val warning: String,
    val year: String,
    val month: String,
    val day: String,
    val isFavorite: Boolean,
    val favoriteNum: Int,
    val saveNum: Int
)
