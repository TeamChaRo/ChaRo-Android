package com.charo.android.domain.repository.home

import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.response.home.ResponseHomeViewData
import com.charo.android.domain.model.StatusCode


interface HomeRepository {
    suspend fun getMain(userEmail : String) : ResponseHomeViewData


    suspend fun postLike(requestHomeLikeData: RequestHomeLikeData) : StatusCode
}