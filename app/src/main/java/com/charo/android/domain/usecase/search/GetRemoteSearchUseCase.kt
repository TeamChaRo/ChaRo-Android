package com.charo.android.domain.usecase.search

import com.charo.android.data.mapper.SearchMapper
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.domain.model.search.SearchDrive
import com.charo.android.domain.repository.search.SearchViewRepository

class GetRemoteSearchUseCase(private val repository : SearchViewRepository) {
    suspend fun execute(searchRequestDrive: RequestSearchViewData) : List<SearchDrive>{
        return SearchMapper.mapperToSearch(repository.postSearch(searchRequestDrive))
    }
}