package com.charo.android.data.datasource.remote.search

import com.example.charo_android.data.api.search.SearchViewService
import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.data.model.response.search.ResponseSearchViewData

class RemoteSearchViewDataSourceImpl(private val service: SearchViewService) :
    RemoteSearchViewDataSource {
    override suspend fun postSearch(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData
    = service.postSearch(requestSearchViewData)
}