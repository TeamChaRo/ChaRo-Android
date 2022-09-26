package com.charo.android.data.datasource.local.home

import com.charo.android.domain.model.home.BannerRoad

interface LocalHomeBannerRoadDataSource {

    fun fetchData() : List<BannerRoad>
}