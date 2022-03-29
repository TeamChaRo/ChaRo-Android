package com.charo.android.data.datasource.local.home

import com.example.charo_android.domain.model.home.BannerRoad

interface LocalHomeBannerRoadDataSource {

    fun fetchData() : List<BannerRoad>
}