package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.repository.moreview.MoreViewRepository


class GetRemoteMoreDriveUseCase(private val repository: MoreViewRepository) {
    suspend fun execute(userEmail:String, identifer: String, value : String) : List<MoreDrive>{
        return MoreViewMapper.mapperToMoreDrive(repository.getMoreView(userEmail, identifer, value))
    }

}