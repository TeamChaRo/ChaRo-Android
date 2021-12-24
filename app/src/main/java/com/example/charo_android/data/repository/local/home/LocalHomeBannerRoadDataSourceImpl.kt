package com.example.charo_android.data.repository.local.home

import com.example.charo_android.R
import com.example.charo_android.domain.model.home.BannerRoad

class LocalHomeBannerRoadDataSourceImpl() : LocalHomeBannerRoadDataSource {
    override fun fetchData(): List<BannerRoad> {
        return listOf(
            BannerRoad(R.drawable.road_1),
            BannerRoad(R.drawable.road_2),
            BannerRoad(R.drawable.road_3),
            BannerRoad(R.drawable.road_4)
        )

    }
}