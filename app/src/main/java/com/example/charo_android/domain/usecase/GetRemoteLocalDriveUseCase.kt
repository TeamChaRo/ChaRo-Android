package com.example.charo_android.domain.usecase




import com.example.charo_android.data.mapper.mapperToLocalDrive
import com.example.charo_android.domain.model.home.LocalDrive
import com.example.charo_android.domain.repository.HomeRepository

class GetRemoteLocalDriveUseCase(private val repository: HomeRepository) {
    suspend fun execute(parameter: String): List<LocalDrive> {
        return mapperToLocalDrive(repository.getMain(parameter))
    }
}