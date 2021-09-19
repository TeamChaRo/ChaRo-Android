package com.example.charo_android.domain.usecase

import com.example.charo_android.data.mapper.HomeMapper
import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.domain.model.home.CustomThemeDrive
import com.example.charo_android.domain.repository.HomeRepository

class GetRemoteCustomThemeUseCase(private val repository: HomeRepository)  {
    suspend fun execute(parameter: String): List<CustomThemeDrive> {
        return HomeMapper.mapperToCustomThemeDrive(repository.getMain(parameter))
    }
}