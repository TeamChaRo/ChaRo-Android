package com.example.charo_android.data.model.entity

import com.google.gson.annotations.SerializedName

data class CustomThemeDriveEntity(
    @SerializedName("image")
    val homeNightDriveImage : Int,

    @SerializedName("title")
    val homeNightDriveTitle : String,

    @SerializedName("region")
    val homeNightDriveChip_1 : String,

    @SerializedName("theme")
    val homeNightDriveChip_2 : String,

    @SerializedName("warning")
    val homeNightDriveChip_3 : String,

    @SerializedName("isFavorite")
    val homeNightDriveHeart : Boolean,
)
