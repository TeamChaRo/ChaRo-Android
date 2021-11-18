package com.example.charo_android.domain.usecase.home

import com.example.charo_android.data.mapper.HomeMapper
import com.example.charo_android.domain.model.home.*
import com.example.charo_android.domain.repository.home.HomeRepository

class GetRemoteBannerUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<Banner> {
        return HomeMapper.mapperToBanner(repository.getMain(parameter))
    }

}








