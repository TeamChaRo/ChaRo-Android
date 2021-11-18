package com.example.charo_android.domain.usecase.home

import com.example.charo_android.data.mapper.HomeMapper
import com.example.charo_android.domain.model.home.Title
import com.example.charo_android.domain.repository.home.HomeRepository

class GetRemoteHomeTitle(private val repository: HomeRepository) {
    suspend fun execute(parameter : String) : Title {
        return HomeMapper.mapperToHomeTitle(repository.getMain(parameter))
    }
}