package com.charo.android.data.datasource.remote.home

import com.charo.android.data.api.home.HomeViewService
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.home.ResponseHomeViewData


class RemoteHomeDataSourceImpl(private val service : HomeViewService) : RemoteHomeDataSource {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData = service.getMain(userEmail)

    override suspend fun postLike(requestHomeLikeData: RequestHomeLikeData): ResponseStatusCode {
        return service.postLike(requestHomeLikeData)
    }
}