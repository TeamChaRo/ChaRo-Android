package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeViewPagerImage

class LocalHomeViewPagerDataSource : HomeViewPagerDataSource {
    override fun fetchData(): MutableList<HomeViewPagerImage> {
        return mutableListOf<HomeViewPagerImage>(
            HomeViewPagerImage(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerImage(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerImage(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerImage(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerImage(
                homeViewPagerImage = R.drawable.main_image
            )
        )
    }
}