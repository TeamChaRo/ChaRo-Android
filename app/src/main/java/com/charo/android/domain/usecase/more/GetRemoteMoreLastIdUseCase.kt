package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.LastId
import com.charo.android.domain.repository.moreview.MoreViewRepository

class GetRemoteMoreLastIdUseCase(private val repository: MoreViewRepository) {
    suspend fun execute(userEmail:String, identifer: String, value : String) : LastId {
        return MoreViewMapper.mapperToMoreLastId(repository.getMoreView(userEmail, identifer, value))
    }
}