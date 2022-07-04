package com.charo.android.data.model.mypage

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("theme")
    val theme: String,
    @SerializedName("warning")
    val warning: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("month")
    val month: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("isFavorite")
    val isFavorite: Boolean,
    @SerializedName("favoriteNum")
    var favoriteNum: Int,
    @SerializedName("saveNum")
    var saveNum: Int
)
