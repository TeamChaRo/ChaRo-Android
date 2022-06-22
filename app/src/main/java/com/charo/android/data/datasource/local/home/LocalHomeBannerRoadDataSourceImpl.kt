package com.charo.android.data.datasource.local.home

import com.charo.android.R
import com.charo.android.domain.model.home.BannerRoad

// 확인요망
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