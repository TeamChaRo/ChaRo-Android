package com.example.charo_android.data.model.entity

import com.google.gson.annotations.SerializedName

data class TodayCharoDriveEntity(
    @SerializedName("image")
    val homeTodayDriveImage : Int,

    @SerializedName("title")
    val homeTodayDriveTitle : String,

    @SerializedName("region")
    val homeTodayDriveChip_1 : String,

    @SerializedName("theme")
    val homeTodayDriveChip_2 : String,

    @SerializedName("warning")
    val homeTodayDriveChip_3 : String,

    @SerializedName("isFavorite")
    val homeTodayDriveHeart : Boolean,
)
