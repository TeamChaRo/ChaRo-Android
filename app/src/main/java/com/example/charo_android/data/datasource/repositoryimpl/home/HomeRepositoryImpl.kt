package com.example.charo_android.data.datasource.repositoryimpl.home

import com.example.charo_android.data.mapper.HomeMapper
import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.data.model.response.home.ResponseHomeViewData
import com.example.charo_android.data.datasource.remote.home.RemoteHomeDataSource
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.home.HomeRepository

class HomeRepositoryImpl(private val remoteDataSource : RemoteHomeDataSource): HomeRepository {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData {
        return remoteDataSource.getMain(userEmail)
    }


    override suspend fun postLike(requestHomeLikeData: RequestHomeLikeData): StatusCode {
        return HomeMapper.mapperToHomeLike(remoteDataSource.postLike(requestHomeLikeData))
    }
}