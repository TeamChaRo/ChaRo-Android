package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeViewPagerInfo

class LocalHomeViewPagerDataSource : HomeViewPagerDataSource {
    override fun fetchData(): MutableList<HomeViewPagerInfo> {
        return mutableListOf<HomeViewPagerInfo>(
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image
            )
        )
    }
}