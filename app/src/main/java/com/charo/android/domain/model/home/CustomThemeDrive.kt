package com.charo.android.domain.model.home

data class CustomThemeDrive(
    val homeNightDriveImage : String,
    val homeNightDriveTitle : String,
    val homeNightDriveChip_1 : String?="",
    val homeNightDriveChip_2 : String?="",
    val homeNightDriveChip_3 : String?="",
    val homeNightDriveHeart : Boolean,
    val homeNightDriveDay: String,
    val homeNightDriveMonth: String,
    val homeNightDrivePostId: Int,
    val homeNightDriveYear: String
)
