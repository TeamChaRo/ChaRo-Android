package com.charo.android.data

data class SavedPost(
    val day: String,
    val favoriteNum: Int,
    val image: String,
    val month: String,
    val postId: Int,
    val saveNum: Int,
    val tags: List<String>,
    val title: String,
    val year: String
)