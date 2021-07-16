package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeViewPagerInfo

class LocalHomeViewPagerDataSource : HomeViewPagerDataSource {
    override fun fetchData(): MutableList<HomeViewPagerInfo> {
        return mutableListOf<HomeViewPagerInfo>(
            HomeViewPagerInfo(
                homeViewPagerRoadImage = R.drawable.road_1
            ),
            HomeViewPagerInfo(

                homeViewPagerRoadImage = R.drawable.road_2
            ),
            HomeViewPagerInfo(
                homeViewPagerRoadImage = R.drawable.road_3
            ),
            HomeViewPagerInfo(

                homeViewPagerRoadImage = R.drawable.road_4
            ),

        )
    }
}