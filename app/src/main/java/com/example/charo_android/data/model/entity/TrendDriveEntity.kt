package com.example.charo_android.data.model.entity

import com.google.gson.annotations.SerializedName

data class TrendDriveEntity(
    @SerializedName("image")
    val homeHotDriveImage : Int,

    @SerializedName("title")
    val homeHotDriveTitle : String,

    @SerializedName("region")
    val homeHotDriveChip_1 : String,

    @SerializedName("theme")
    val homeHotDriveChip_2 : String,

    @SerializedName("warning")
    val homeHotDriveChip_3 : String,

    @SerializedName("isFavorite")
    val homeHotDriveHeart : Boolean,
)
