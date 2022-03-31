package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper
import com.charo.android.domain.model.home.CustomThemeDrive
import com.charo.android.domain.repository.home.HomeRepository


class GetRemoteCustomThemeUseCase(private val repository: HomeRepository)  {
    suspend fun execute(parameter: String): List<CustomThemeDrive> {
        return HomeMapper.mapperToCustomThemeDrive(repository.getMain(parameter))
    }
}