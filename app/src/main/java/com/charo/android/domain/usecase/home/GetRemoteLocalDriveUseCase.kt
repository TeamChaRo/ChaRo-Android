package com.charo.android.domain.usecase.home




import com.example.charo_android.data.mapper.HomeMapper.mapperToLocalDrive
import com.example.charo_android.domain.model.home.LocalDrive
import com.example.charo_android.domain.repository.home.HomeRepository

class GetRemoteLocalDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<LocalDrive> {
        return mapperToLocalDrive(repository.getMain(parameter))
    }
}