package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeThemeInfo

class LocalHomeThemeDataSource: HomeThemeDataSource {
    override fun fetchData(): MutableList<HomeThemeInfo> {
        return mutableListOf<HomeThemeInfo>(
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),
            HomeThemeInfo(
                homeThemeImage = R.drawable.image_home_theme,
                homeThemeTitle = "#벚꽃"
            ),

        )
    }
}