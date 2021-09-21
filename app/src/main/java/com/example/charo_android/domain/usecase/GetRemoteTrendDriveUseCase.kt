package com.example.charo_android.domain.usecase


import com.example.charo_android.data.mapper.mapperToTrendDrive
import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.domain.model.home.TrendDrive
import com.example.charo_android.domain.repository.HomeRepository

class GetRemoteTrendDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<TrendDrive> {
        return mapperToTrendDrive(repository.getMain(parameter))
    }
}