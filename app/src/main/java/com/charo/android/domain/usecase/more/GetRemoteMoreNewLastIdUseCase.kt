package com.charo.android.domain.usecase.more

import com.example.charo_android.data.mapper.MoreViewMapper
import com.example.charo_android.domain.model.more.LastId
import com.example.charo_android.domain.repository.moreview.MoreNewViewRepository

class GetRemoteMoreNewLastIdUseCase(private val repository : MoreNewViewRepository) {

    suspend fun execute(userEmail:String, identifer: String, value : String) : LastId{
        return MoreViewMapper.mapperToMoreLastId(repository.getMoreNewView(userEmail, identifer, value))

    }
}