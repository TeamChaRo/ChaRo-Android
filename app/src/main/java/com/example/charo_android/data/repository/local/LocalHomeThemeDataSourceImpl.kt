package com.example.charo_android.data.repository.local

import com.example.charo_android.R
import com.example.charo_android.data.model.entity.HomeThemeInfo

class LocalHomeThemeDataSourceImpl: LocalHomeThemeDataSource {
    override fun fetchData(): MutableList<HomeThemeInfo> {
        return mutableListOf<HomeThemeInfo>(
            HomeThemeInfo(
                homeThemeImage = R.drawable.mouantin,
                homeThemeTitle = "#산"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.sea,
                homeThemeTitle = "#바다"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.lake,
                homeThemeTitle = "#호수"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.river,
                homeThemeTitle = "#강"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.spring,
                homeThemeTitle = "#봄"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.summer,
                homeThemeTitle = "#여름"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.fall,
                homeThemeTitle = "#가을"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.winter,
                homeThemeTitle = "#겨울"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.searoad,
                homeThemeTitle = "#해안도로"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.blossom,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.maple,
                homeThemeTitle = "#단풍"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.relax,
                homeThemeTitle = "#여유"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.speed,
                homeThemeTitle = "#스피드"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.nightview,
                homeThemeTitle = "#야경"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.city,
                homeThemeTitle = "#도심"
            ),
        )
    }
}