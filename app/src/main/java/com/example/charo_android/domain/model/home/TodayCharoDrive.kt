package com.example.charo_android.domain.model.home

data class TodayCharoDrive(
    val homeTodayDriveImage : String,
    val homeTodayDriveTitle : String,
    val homeTodayDriveChip_1 : String?= "",
    val homeTodayDriveChip_2 : String?= "",
    val homeTodayDriveChip_3 : String?="",
    val homeTodayDriveHeart : Boolean,
    val homeTodayDriveDay: String,
    val homeTodayDriveMonth: String,
    val homeTodayDrivePostId: Int,
    val homeTodayDriveYear: String
)
