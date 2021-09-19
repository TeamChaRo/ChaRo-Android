package com.example.charo_android.data.model.entity

import com.google.gson.annotations.SerializedName

data class LocalDriveEntity(
    @SerializedName("image")
    val homeLocationDriveImage : Int,

    @SerializedName("title")
    val homeLocationDriveTitle : String,

    @SerializedName("region")
    val homeLocationDriveChip_1 : String,

    @SerializedName("theme")
    val homeLocationDriveChip_2 : String,

    @SerializedName("warning")
    val homeLocationDriveChip_3 : String,

    @SerializedName("isFavorite")
    val homeLocationDriveHeart : Boolean,
)
