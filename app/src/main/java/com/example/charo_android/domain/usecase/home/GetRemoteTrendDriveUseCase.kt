package com.example.charo_android.domain.usecase.home


import com.example.charo_android.data.mapper.HomeMapper.mapperToTrendDrive
import com.example.charo_android.domain.model.home.TrendDrive
import com.example.charo_android.domain.repository.home.HomeRepository

class GetRemoteTrendDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<TrendDrive> {
        return mapperToTrendDrive(repository.getMain(parameter))
    }
}