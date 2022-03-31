package com.charo.android.domain.model.home

data class LocalDrive(
    val homeLocationDriveImage : String,
    val homeLocationDriveTitle : String,
    val homeLocationDriveChip_1 : String?="",
    val homeLocationDriveChip_2 : String?="",
    val homeLocationDriveChip_3 : String?="",
    val homeLocationDriveHeart : Boolean,
    val homeLocationDriveDay: String,
    val homeLocationDriveMonth: String,
    val homeLocationDrivePostId: Int,
    val homeLocationDriveYear: String
)
