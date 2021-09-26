package com.example.charo_android.domain.usecase

import com.example.charo_android.data.mapper.MoreViewMapper
import com.example.charo_android.domain.model.more.LastId
import com.example.charo_android.domain.repository.MoreViewRepository

class GetRemoteMoreLastIdUseCase(private val repository: MoreViewRepository) {
    suspend fun execute(userEmail:String, identifer: String, value : String) : LastId {
        return MoreViewMapper.mapperToMoreLastId(repository.getMoreView(userEmail, identifer, value))
    }
}