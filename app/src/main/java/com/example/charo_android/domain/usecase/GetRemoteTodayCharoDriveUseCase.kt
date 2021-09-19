package com.example.charo_android.domain.usecase


import com.example.charo_android.data.mapper.mapperToTodayCharoDrive
import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.domain.model.home.TodayCharoDrive
import com.example.charo_android.domain.repository.HomeRepository

class GetRemoteTodayCharoDriveUseCase(private val repository: HomeRepository)  {
    suspend fun execute(parameter: String): List<TodayCharoDrive> {
        return mapperToTodayCharoDrive(repository.getMain(parameter))
    }
}