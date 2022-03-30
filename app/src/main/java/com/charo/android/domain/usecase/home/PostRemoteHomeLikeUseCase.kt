package com.charo.android.domain.usecase.home

import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.home.HomeRepository

class PostRemoteHomeLikeUseCase(private val repository : HomeRepository) {
    suspend fun execute(requestHomeLikeData: RequestHomeLikeData) : StatusCode {
        return repository.postLike(requestHomeLikeData)
    }
}