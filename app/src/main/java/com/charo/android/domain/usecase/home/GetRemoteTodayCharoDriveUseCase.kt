package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper.mapperToTodayCharoDrive
import com.charo.android.domain.model.home.TodayCharoDrive
import com.charo.android.domain.repository.home.HomeRepository


class GetRemoteTodayCharoDriveUseCase(private val repository: HomeRepository)  {
    suspend fun execute(parameter: String): List<TodayCharoDrive> {
        return mapperToTodayCharoDrive(repository.getMain(parameter))
    }
}