package com.charo.android.domain.model.home

data class TrendDrive(
    val homeTrendDriveImage : String,
    val homeTrendDriveTitle : String,
    val homeTrendDriveChip_1 : String,
    val homeTrendDriveChip_2 : String,
    val homeTrendDriveChip_3 : String ?= "",
    var homeTrendDriveHeart : Boolean,
    val homeTrendDriveDay: String,
    val homeTrendDriveMonth: String,
    val homeTrendDrivePostId: Int,
    val homeTrendDriveYear: String
)
