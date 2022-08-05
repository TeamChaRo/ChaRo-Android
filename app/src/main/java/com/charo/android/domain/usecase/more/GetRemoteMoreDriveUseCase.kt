package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.repository.moreview.MoreViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class GetRemoteMoreDriveUseCase(private val repository: MoreViewRepository) {
    operator fun invoke(userEmail: String, identifer: String, value: String): Flow<List<MoreDrive>> {
        return  flow {
            repository.getMoreView(userEmail, identifer, value).collect{
                emit(MoreViewMapper.mapperToMoreDrive(it))
            }
        }
    }
}