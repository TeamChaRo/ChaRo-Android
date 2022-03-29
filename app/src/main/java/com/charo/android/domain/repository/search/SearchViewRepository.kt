package com.charo.android.domain.repository.search

import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.data.model.response.search.ResponseSearchViewData

interface SearchViewRepository {
    suspend fun postSearch(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData
}