package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeTodayDriverInfo

class LocalHomeTodayDriveDataSource : HomeTodayDriveDataSource {
    override fun fetchData(): MutableList<HomeTodayDriverInfo> {
        return mutableListOf<HomeTodayDriverInfo>(
            HomeTodayDriverInfo(
                homeTodayDriveImage = R.drawable.home_today_drive_image,
                homeTodayDriveTitle = "해안 도로 정복할 수 있는 포항 드라이브 코스 다같이 지금 바로 출발해요",
                homeTodayDriveChip_1 = "#7번 국도",
                homeTodayDriveChip_2 = "#바다",
                homeTodayDriveChip_3 = "#포항",
                homeTodayDriveHeart = false
            ),
            HomeTodayDriverInfo(
                homeTodayDriveImage = R.drawable.home_today_drive_image,
                homeTodayDriveTitle = "해안 도로 정복할 수 있는 포항 드라이브 코스 다같이 지금 바로 출발해요",
                homeTodayDriveChip_1 = "#7번 국도",
                homeTodayDriveChip_2 = "#바다",
                homeTodayDriveChip_3 = "#포항",
                homeTodayDriveHeart = false
            ),
            HomeTodayDriverInfo(
                homeTodayDriveImage = R.drawable.home_today_drive_image,
                homeTodayDriveTitle = "해안 도로 정복할 수 있는 포항 드라이브 코스 다같이 지금 바로 출발해요",
                homeTodayDriveChip_1 = "#7번 국도",
                homeTodayDriveChip_2 = "#바다",
                homeTodayDriveChip_3 = "#포항",
                homeTodayDriveHeart = false
            ),
            HomeTodayDriverInfo(
                homeTodayDriveImage = R.drawable.home_today_drive_image,
                homeTodayDriveTitle = "해안 도로 정복할 수 있는 포항 드라이브 코스 다같이 지금 바로 출발해요",
                homeTodayDriveChip_1 = "#7번 국도",
                homeTodayDriveChip_2 = "#바다",
                homeTodayDriveChip_3 = "#포항",
                homeTodayDriveHeart = false
            ),
            HomeTodayDriverInfo(
                homeTodayDriveImage = R.drawable.home_today_drive_image,
                homeTodayDriveTitle = "해안 도로 정복할 수 있는 포항 드라이브 코스 다같이 지금 바로 출발해요",
                homeTodayDriveChip_1 = "#7번 국도",
                homeTodayDriveChip_2 = "#바다",
                homeTodayDriveChip_3 = "#포항",
                homeTodayDriveHeart = false
            )
        )
    }
}