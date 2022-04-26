package com.charo.android.data.datasource.remote.search

import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData


interface RemoteSearchViewDataSource {
    //검색 인기순
    suspend fun postSearchLike(requestSearchViewData : RequestSearchViewData) : ResponseSearchViewData

    //검색 최신순
    suspend fun postSearchNew(requestSearchViewData : RequestSearchViewData) : ResponseSearchViewData
}