package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.LastId
import com.charo.android.domain.repository.moreview.MoreViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetRemoteMoreLastIdUseCase(private val repository: MoreViewRepository) {
    operator fun invoke(userEmail:String, identifer: String, value : String) : Flow<LastId> {
        return flow {
            repository.getMoreView(userEmail, identifer, value).collect{
                emit(MoreViewMapper.mapperToMoreLastId(it))
            }
        }
    }
}