package com.charo.android.domain.repository.home

import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.data.model.response.home.ResponseHomeViewData
import com.example.charo_android.domain.model.StatusCode

interface HomeRepository {
    suspend fun getMain(userEmail : String) : ResponseHomeViewData


    suspend fun postLike(requestHomeLikeData: RequestHomeLikeData) : StatusCode
}