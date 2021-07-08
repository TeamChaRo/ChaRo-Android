package com.example.charo_android.data

import com.example.charo_android.R
import com.example.charo_android.ui.home.HomeViewPagerInfo

class LocalHomeViewPagerDataSource : HomeViewPagerDataSource {
    override fun fetchData(): MutableList<HomeViewPagerInfo> {
        return mutableListOf<HomeViewPagerInfo>(
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image,
                homeViewPagerTitle = "차로와 함께 \n즐기는 \n드라이브 코스",
                homeViewPagerHashTag = "#날씨도좋은데#바다와함께라면"
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image,
                homeViewPagerTitle = "차로와 함께 \n즐기는 \n드라이브 코스",
                homeViewPagerHashTag = "#날씨도좋은데#바다와함께라면"
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image,
                homeViewPagerTitle = "차로와 함께 \n즐기는 \n드라이브 코스",
                homeViewPagerHashTag = "#날씨도좋은데#바다와함께라면"
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image,
                homeViewPagerTitle = "차로와 함께 \n즐기는 \n드라이브 코스",
                homeViewPagerHashTag = "#날씨도좋은데#바다와함께라면"
            ),
            HomeViewPagerInfo(
                homeViewPagerImage = R.drawable.main_image,
                homeViewPagerTitle = "차로와 함께 \n즐기는 \n드라이브 코스",
                homeViewPagerHashTag = "#날씨도좋은데#바다와함께라면"
            )
        )
    }
}