package com.example.charo_android.data.datasource.remote.search

import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.data.model.response.search.ResponseSearchViewData

interface RemoteSearchViewDataSource {
    suspend fun postSearch(requestSearchViewData : RequestSearchViewData) : ResponseSearchViewData
}