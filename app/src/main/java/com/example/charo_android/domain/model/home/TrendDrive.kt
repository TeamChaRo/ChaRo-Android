package com.example.charo_android.domain.model.home

data class TrendDrive(
    val homeTrendDriveImage : String,
    val homeTrendDriveTitle : String,
    val homeTrendDriveChip_1 : String,
    val homeTrendDriveChip_2 : String,
    val homeTrendDriveChip_3 : String ?= "",
    val homeTrendDriveHeart : Boolean,
    val homeTrendDriveDay: String,
    val homeTrendDriveMonth: String,
    val homeTrendDrivePostId: Int,
    val homeTrendDriveYear: String
)
