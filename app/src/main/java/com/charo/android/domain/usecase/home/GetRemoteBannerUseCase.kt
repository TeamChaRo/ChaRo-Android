package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper
import com.charo.android.domain.model.home.Banner
import com.charo.android.domain.repository.home.HomeRepository


class GetRemoteBannerUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<Banner> {
        return HomeMapper.mapperToBanner(repository.getMain(parameter))
    }

}








