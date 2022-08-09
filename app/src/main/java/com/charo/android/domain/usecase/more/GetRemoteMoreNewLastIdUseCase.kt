package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.LastId
import com.charo.android.domain.repository.moreview.MoreNewViewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetRemoteMoreNewLastIdUseCase(private val repository : MoreNewViewRepository) {

    operator fun invoke(userEmail:String, identifer: String, value : String) : Flow<LastId> {
        return flow {
            repository.getMoreNewView(userEmail, identifer, value).collect{
                emit(MoreViewMapper.mapperToMoreLastId(it))
            }
        }
    }
}