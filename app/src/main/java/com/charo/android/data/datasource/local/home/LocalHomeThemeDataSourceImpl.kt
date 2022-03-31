package com.charo.android.data.datasource.local.home

import com.charo.android.R
import com.charo.android.data.model.entity.HomeThemeInfo


class LocalHomeThemeDataSourceImpl: LocalHomeThemeDataSource {
    override fun fetchData(): MutableList<HomeThemeInfo> {
        return mutableListOf<HomeThemeInfo>(
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_spirng,
                homeThemeTitle = "봄"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_summer,
                homeThemeTitle = "여름"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_fall,
                homeThemeTitle = "가을"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_winter,
                homeThemeTitle = "겨울"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_mouantin,
                homeThemeTitle = "산"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_sea,
                homeThemeTitle = "바다"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_lake,
                homeThemeTitle = "호수"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_river,
                homeThemeTitle = "강"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.ic_sea_road,
                homeThemeTitle = "해안도로"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.blossom,
                homeThemeTitle = "벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.maple,
                homeThemeTitle = "단풍"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.relax,
                homeThemeTitle = "여유"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.speed,
                homeThemeTitle = "스피드"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.nightview,
                homeThemeTitle = "야경"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.city,
                homeThemeTitle = "도심"
            ),
        )
    }
}