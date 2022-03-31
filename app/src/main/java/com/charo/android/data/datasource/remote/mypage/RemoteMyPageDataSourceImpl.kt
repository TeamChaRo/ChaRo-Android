package com.charo.android.data.datasource.remote.mypage

import com.charo.android.data.api.mypage.MyPageService
import com.charo.android.data.model.mypage.ResponseEndlessScroll
import com.charo.android.data.model.mypage.ResponseMyPage


class RemoteMyPageDataSourceImpl(private val service: MyPageService): RemoteMyPageDataSource {
    override suspend fun getLikePost(userEmail: String): ResponseMyPage {
        return service.getLikePost(userEmail)
    }

    override suspend fun getNewPost(userEmail: String): ResponseMyPage {
        return service.getNewPost(userEmail)
    }

    override suspend fun getMoreWrittenLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return service.getMoreWrittenLikePost(userEmail, lastId, lastCount)
    }

    override suspend fun getMoreWrittenNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return service.getMoreWrittenNewPost(userEmail, lastId)
    }

    override suspend fun getMoreSavedLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return service.getMoreSavedLikePost(userEmail, lastId, lastCount)
    }

    override suspend fun getMoreSavedNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return service.getMoreSavedNewPost(userEmail, lastId)
    }
}