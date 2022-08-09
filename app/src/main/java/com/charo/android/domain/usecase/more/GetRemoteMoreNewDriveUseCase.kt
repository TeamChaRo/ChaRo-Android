package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.repository.moreview.MoreNewViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRemoteMoreNewDriveUseCase(private val repository: MoreNewViewRepository) {
     operator fun invoke(userEmail:String, identifer: String, value : String) : Flow<List<MoreDrive>>{
        return flow{
            repository.getMoreNewView(userEmail, identifer, value).collect{
                emit(MoreViewMapper.mapperToMoreDrive(it))
            }
        }
    }
}