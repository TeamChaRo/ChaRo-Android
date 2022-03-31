package com.charo.android.data.datasource.repositoryimpl.search

import com.charo.android.data.datasource.remote.search.RemoteSearchViewDataSource
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData
import com.charo.android.domain.repository.search.SearchViewRepository


class SearchViewRepositoryImpl(private val remoteDataSource: RemoteSearchViewDataSource)
    : SearchViewRepository {
    override suspend fun postSearch(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData {
        return remoteDataSource.postSearch(requestSearchViewData)
    }
}