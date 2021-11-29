package com.example.charo_android.domain.model.search

data class SearchDrive(
    val day: String,
    val image: String,
    val isFavorite: Boolean,
    val month: String,
    val postId: Int,
    val region: String,
    val theme: String,
    val title: String,
    val warning: String,
    val year: String
)