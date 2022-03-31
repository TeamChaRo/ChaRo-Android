package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper.mapperToTrendDrive
import com.charo.android.domain.model.home.TrendDrive
import com.charo.android.domain.repository.home.HomeRepository


class GetRemoteTrendDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<TrendDrive> {
        return mapperToTrendDrive(repository.getMain(parameter))
    }
}