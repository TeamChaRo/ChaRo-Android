package com.example.charo_android.data.repository

import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSource
import com.example.charo_android.domain.repository.HomeRepository

class HomeRepositoryImpl(private val remoteDataSource : RemoteHomeDataSource): HomeRepository {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData {
        return remoteDataSource.getMain(userEmail)

    }
}