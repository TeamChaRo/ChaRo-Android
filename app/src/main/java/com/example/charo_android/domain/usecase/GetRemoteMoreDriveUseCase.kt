package com.example.charo_android.domain.usecase

import com.example.charo_android.data.mapper.MoreViewMapper
import com.example.charo_android.domain.model.more.MoreDrive
import com.example.charo_android.domain.repository.moreview.MoreViewRepository

class GetRemoteMoreDriveUseCase(private val repository: MoreViewRepository) {
    suspend fun execute(userEmail:String, identifer: String, value : String) : List<MoreDrive>{
        return MoreViewMapper.mapperToMoreDrive(repository.getMoreView(userEmail, identifer, value))
    }

}