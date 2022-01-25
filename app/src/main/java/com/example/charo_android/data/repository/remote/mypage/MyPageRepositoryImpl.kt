package com.example.charo_android.data.repository.remote.mypage

import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.mypage.ResponseLikePost
import com.example.charo_android.data.model.mypage.ResponseNewPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPageRepositoryImpl : MyPageRepository {
    override suspend fun getLikePost(userEmail: String): ResponseLikePost {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getLikePost(userEmail)
        }
    }

    override suspend fun getNewPost(userEmail: String): ResponseNewPost {
        return withContext(Dispatchers.IO) {
            ApiService.myPageService.getNewPost(userEmail)
        }
    }
}