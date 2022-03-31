package com.charo.android.domain.repository.search

import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData


interface SearchViewRepository {
    suspend fun postSearch(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData
}