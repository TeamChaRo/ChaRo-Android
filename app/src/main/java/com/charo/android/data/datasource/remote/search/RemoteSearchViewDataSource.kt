package com.charo.android.data.datasource.remote.search

import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData


interface RemoteSearchViewDataSource {
    suspend fun postSearch(requestSearchViewData : RequestSearchViewData) : ResponseSearchViewData
}