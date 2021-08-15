package com.example.charo_android.data.repository

import com.example.charo_android.data.datasource.home.HomeDataSource
import com.example.charo_android.data.response.ResponseHomeViewData

class HomeRepositoryImpl(private val remoteDataSource : HomeDataSource): HomeRepository {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData = remoteDataSource.getMain(userEmail)
}