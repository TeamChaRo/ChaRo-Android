package com.example.charo_android.data.datasource.repositoryimpl.search

import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.data.model.response.search.ResponseSearchViewData
import com.example.charo_android.data.datasource.remote.search.RemoteSearchViewDataSource
import com.example.charo_android.domain.repository.search.SearchViewRepository

class SearchViewRepositoryImpl(private val remoteDataSource: RemoteSearchViewDataSource)
    : SearchViewRepository{
    override suspend fun postSearch(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData {
        return remoteDataSource.postSearch(requestSearchViewData)
    }
}