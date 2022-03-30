package com.charo.android.domain.usecase.home

import com.charo.android.data.mapper.HomeMapper
import com.charo.android.domain.repository.home.HomeRepository
import com.charo.android.domain.model.home.Title

class GetRemoteHomeTitle(private val repository: HomeRepository) {
    suspend fun execute(parameter : String) : Title {
        return HomeMapper.mapperToHomeTitle(repository.getMain(parameter))
    }
}