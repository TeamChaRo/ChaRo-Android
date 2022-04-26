package com.charo.android.domain.usecase.search

import com.charo.android.data.mapper.SearchMapper
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.domain.model.search.SearchDrive
import com.charo.android.domain.repository.search.SearchViewRepository

class GetRemoteSearchUseCase(private val repository : SearchViewRepository) {

    //검색 인기순
    suspend fun execute(searchRequestDrive: RequestSearchViewData) : List<SearchDrive>{
        return SearchMapper.mapperToSearch(repository.postSearchLike(searchRequestDrive))
    }

    //검색 최신순
    suspend fun executeNew(searchRequestDrive: RequestSearchViewData) : List<SearchDrive>{
        return SearchMapper.mapperToSearch(repository.postSearchNew(searchRequestDrive))
    }
}