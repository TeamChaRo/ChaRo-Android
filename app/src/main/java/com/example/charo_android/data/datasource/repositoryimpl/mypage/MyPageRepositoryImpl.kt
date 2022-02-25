package com.example.charo_android.data.datasource.repositoryimpl.mypage

import com.example.charo_android.data.model.mypage.ResponseEndlessScroll
import com.example.charo_android.data.model.mypage.ResponseMyPage
import com.example.charo_android.data.datasource.remote.mypage.RemoteMyPageDataSource
import com.example.charo_android.domain.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(private val remoteDataSource: RemoteMyPageDataSource): MyPageRepository {
    override suspend fun getLikePost(userEmail: String): ResponseMyPage {
        return remoteDataSource.getLikePost(userEmail)
    }

    override suspend fun getNewPost(userEmail: String): ResponseMyPage {
        return remoteDataSource.getNewPost(userEmail)
    }

    override suspend fun getMoreWrittenLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return remoteDataSource.getMoreWrittenLikePost(userEmail, lastId, lastCount)
    }

    override suspend fun getMoreWrittenNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return remoteDataSource.getMoreWrittenNewPost(userEmail, lastId)
    }

    override suspend fun getMoreSavedLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return remoteDataSource.getMoreSavedLikePost(userEmail, lastId, lastCount)
    }

    override suspend fun getMoreSavedNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return remoteDataSource.getMoreSavedNewPost(userEmail, lastId)
    }

}