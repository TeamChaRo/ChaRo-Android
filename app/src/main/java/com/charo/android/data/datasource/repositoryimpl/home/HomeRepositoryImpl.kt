package com.charo.android.data.datasource.repositoryimpl.home

import com.charo.android.data.datasource.remote.home.RemoteHomeDataSource
import com.charo.android.data.mapper.HomeMapper
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.response.home.ResponseHomeViewData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.home.HomeRepository

class HomeRepositoryImpl(private val remoteDataSource : RemoteHomeDataSource): HomeRepository {
    override suspend fun getMain(userEmail: String): ResponseHomeViewData {
        return remoteDataSource.getMain(userEmail)
    }


    override suspend fun postLike(requestHomeLikeData: RequestHomeLikeData): StatusCode {
        return HomeMapper.mapperToHomeLike(remoteDataSource.postLike(requestHomeLikeData))
    }
}