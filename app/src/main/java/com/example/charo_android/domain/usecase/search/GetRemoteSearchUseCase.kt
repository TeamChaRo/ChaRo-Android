package com.example.charo_android.domain.usecase.search

import com.example.charo_android.data.mapper.SearchMapper
import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.domain.model.search.SearchDrive
import com.example.charo_android.domain.repository.search.SearchViewRepository

class GetRemoteSearchUseCase(private val repository : SearchViewRepository) {
    suspend fun execute(searchRequestDrive: RequestSearchViewData) : List<SearchDrive>{
        return SearchMapper.mapperToSearch(repository.postSearch(searchRequestDrive))
    }
}