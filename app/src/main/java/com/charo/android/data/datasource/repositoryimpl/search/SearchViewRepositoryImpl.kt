package com.charo.android.data.datasource.repositoryimpl.search

import com.charo.android.data.datasource.remote.search.RemoteSearchViewDataSource
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData
import com.charo.android.domain.repository.search.SearchViewRepository


class SearchViewRepositoryImpl(private val remoteDataSource: RemoteSearchViewDataSource)
    : SearchViewRepository {

    //검색 인기순
    override suspend fun postSearchLike(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData {
        return remoteDataSource.postSearchLike(requestSearchViewData)
    }

    //검색 최신순
    override suspend fun postSearchNew(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData {
        return remoteDataSource.postSearchNew(requestSearchViewData)
    }
}