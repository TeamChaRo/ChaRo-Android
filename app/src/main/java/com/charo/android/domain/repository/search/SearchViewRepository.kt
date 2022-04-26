package com.charo.android.domain.repository.search

import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData


interface SearchViewRepository {
    //검색 인기순
    suspend fun postSearchLike(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData

    //검색 최신순
    suspend fun postSearchNew(requestSearchViewData: RequestSearchViewData): ResponseSearchViewData
}