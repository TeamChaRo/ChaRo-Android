package com.example.charo_android.data.repository.home

import com.example.charo_android.data.model.response.home.ResponseHomeViewData
import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSource
import com.example.charo_android.domain.model.home.BannerRoad
import com.example.charo_android.domain.repository.home.HomeRepository

class HomeRepositoryImpl(private val remoteDataSource : RemoteHomeDataSource): HomeRepository {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData {
        return remoteDataSource.getMain(userEmail)
    }


}