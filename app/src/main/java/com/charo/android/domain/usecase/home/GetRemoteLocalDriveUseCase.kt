package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper.mapperToLocalDrive
import com.charo.android.domain.model.home.LocalDrive
import com.charo.android.domain.repository.home.HomeRepository


class GetRemoteLocalDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<LocalDrive> {
        return mapperToLocalDrive(repository.getMain(parameter))
    }
}