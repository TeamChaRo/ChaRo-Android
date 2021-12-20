package com.example.charo_android.data.repository.local.home

import com.example.charo_android.domain.model.home.BannerRoad

interface LocalHomeBannerRoadDataSource {

    fun fetchData() : List<BannerRoad>
}