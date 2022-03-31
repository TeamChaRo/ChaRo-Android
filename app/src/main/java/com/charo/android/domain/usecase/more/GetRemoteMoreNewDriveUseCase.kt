package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.repository.moreview.MoreNewViewRepository

class GetRemoteMoreNewDriveUseCase(private val repository: MoreNewViewRepository) {
    suspend fun execute(userEmail:String, identifer: String, value : String) : List<MoreDrive>{
        return MoreViewMapper.mapperToMoreDrive(repository.getMoreNewView(userEmail, identifer, value))
    }
}