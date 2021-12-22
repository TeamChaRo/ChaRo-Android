package com.example.charo_android.domain.usecase.home

import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.home.HomeRepository

class PostRemoteHomeLikeUseCase(private val repository : HomeRepository) {
    suspend fun execute(requestHomeLikeData: RequestHomeLikeData) : StatusCode{
        return repository.postLike(requestHomeLikeData)
    }
}