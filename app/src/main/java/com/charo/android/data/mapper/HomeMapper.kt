package com.charo.android.data.mapper

import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.home.ResponseHomeViewData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.model.home.*

object HomeMapper {

    fun mapperToBanner(responseHomeViewData: ResponseHomeViewData): List<Banner> {
        return responseHomeViewData.data.banner.map {
            Banner(
                homeViewPagerRoadImage = it.bannerImage,
                homeViewPagerTag = it.bannerTag,
                homeViewPagerTitle = it.bannerTitle
            )
        }
    }


    fun mapperToCustomThemeDrive(responseHomeViewData: ResponseHomeViewData): List<CustomThemeDrive> {
        return responseHomeViewData.data.customDrive.drive.map {
            CustomThemeDrive(
                homeNightDriveImage = it.image,
                homeNightDriveTitle = it.title,
                homeNightDriveChip_1 = it.region,
                homeNightDriveChip_2 = it.theme,
                homeNightDriveChip_3 = it.warning,
                homeNightDriveHeart = it.isFavorite,
                homeNightDriveDay = it.day,
                homeNightDriveMonth = it.month,
                homeNightDrivePostId = it.postId,
                homeNightDriveYear = it.year
            )
        }
    }


    fun mapperToLocalDrive(responseHomeViewData: ResponseHomeViewData): List<LocalDrive> {
        return responseHomeViewData.data.localDrive.drive.map {
            LocalDrive(
                homeLocationDriveImage = it.image,
                homeLocationDriveTitle = it.title,
                homeLocationDriveChip_1 = it.region,
                homeLocationDriveChip_2 = it.theme,
                homeLocationDriveChip_3 = it.warning,
                homeLocationDriveHeart = it.isFavorite,
                homeLocationDriveDay = it.day,
                homeLocationDriveMonth = it.month,
                homeLocationDrivePostId = it.postId,
                homeLocationDriveYear = it.year

            )
        }
    }

    fun mapperToTodayCharoDrive(responseHomeViewData: ResponseHomeViewData): List<TodayCharoDrive> {
        return responseHomeViewData.data.todayCharoDrive.drive.map {
            TodayCharoDrive(
                homeTodayDriveImage = it.image,
                homeTodayDriveTitle = it.title,
                homeTodayDriveChip_1 = it.region,
                homeTodayDriveChip_2 = it.theme,
                homeTodayDriveChip_3 = it.warning,
                homeTodayDriveHeart = it.isFavorite,
                homeTodayDriveDay = it.day,
                homeTodayDriveMonth = it.month,
                homeTodayDrivePostId = it.postId,
                homeTodayDriveYear = it.year
            )
        }
    }

    fun mapperToTrendDrive(responseHomeViewData: ResponseHomeViewData): List<TrendDrive> {
        return responseHomeViewData.data.trendDrive.drive.map {
            TrendDrive(
                homeTrendDriveImage = it.image,
                homeTrendDriveTitle = it.title,
                homeTrendDriveChip_1 = it.region,
                homeTrendDriveChip_2 = it.theme,
                homeTrendDriveChip_3 = it.warning,
                homeTrendDriveHeart = it.isFavorite,
                homeTrendDriveDay = it.day,
                homeTrendDriveMonth = it.month,
                homeTrendDrivePostId = it.postId,
                homeTrendDriveYear = it.year
            )
        }


    }

    fun mapperToHomeTitle(responseHomeViewData: ResponseHomeViewData): Title {
        return Title(
            customTitle = responseHomeViewData.data.customTitle,
            localTitle = responseHomeViewData.data.localTitle
        )
    }

    fun mapperToHomeLike(responseStatusCode : ResponseStatusCode) : StatusCode{
        return StatusCode(responseStatusCode.success)
    }
}

