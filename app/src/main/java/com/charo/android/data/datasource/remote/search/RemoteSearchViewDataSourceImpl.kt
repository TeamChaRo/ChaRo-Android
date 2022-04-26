package com.charo.android.data.datasource.remote.search

import com.charo.android.data.api.search.SearchViewService
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData


class RemoteSearchViewDataSourceImpl(private val service: SearchViewService) :
    RemoteSearchViewDataSource {
    override suspend fun postSearchLike(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData
    = service.postSearchLike(requestSearchViewData)


    override suspend fun postSearchNew(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData {
        return service.postSearchNew(requestSearchViewData)
    }
}