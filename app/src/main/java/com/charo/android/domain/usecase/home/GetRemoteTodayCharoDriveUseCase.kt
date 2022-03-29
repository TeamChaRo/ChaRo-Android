package com.charo.android.domain.usecase.home


import com.example.charo_android.data.mapper.HomeMapper.mapperToTodayCharoDrive
import com.example.charo_android.domain.model.home.TodayCharoDrive
import com.example.charo_android.domain.repository.home.HomeRepository

class GetRemoteTodayCharoDriveUseCase(private val repository: HomeRepository)  {
    suspend fun execute(parameter: String): List<TodayCharoDrive> {
        return mapperToTodayCharoDrive(repository.getMain(parameter))
    }
}