package com.charo.android.domain.usecase.more

import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.LastId
import com.charo.android.domain.repository.moreview.MoreNewViewRepository

class GetRemoteMoreNewLastIdUseCase(private val repository : MoreNewViewRepository) {

    suspend fun execute(userEmail:String, identifer: String, value : String) : LastId {
        return MoreViewMapper.mapperToMoreLastId(repository.getMoreNewView(userEmail, identifer, value))

    }
}