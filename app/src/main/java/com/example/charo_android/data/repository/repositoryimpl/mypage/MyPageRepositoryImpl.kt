package com.example.charo_android.data.repository.repositoryimpl.mypage

import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.mypage.*
import com.example.charo_android.data.repository.remote.mypage.MyPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPageRepositoryImpl : MyPageRepository {
    override suspend fun getLikePost(userEmail: String): ResponseMyPage {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getLikePost(userEmail)
        }
    }

    override suspend fun getNewPost(userEmail: String): ResponseMyPage {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getNewPost(userEmail)
        }
    }

    override suspend fun getMoreWrittenLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getMoreWrittenLikePost(userEmail, lastId, lastCount)
        }
    }

    override suspend fun getMoreWrittenNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getMoreWrittenNewPost(userEmail, lastId)
        }
    }

    override suspend fun getMoreSavedLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getMoreSavedLikePost(userEmail, lastId, lastCount)
        }
    }

    override suspend fun getMoreSavedNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getMoreSavedNewPost(userEmail, lastId)
        }
    }
}