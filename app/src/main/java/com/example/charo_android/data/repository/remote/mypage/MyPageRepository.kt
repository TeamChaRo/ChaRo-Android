package com.example.charo_android.data.repository.remote.mypage

import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.mypage.ResponseLikePost
import com.example.charo_android.data.model.mypage.ResponseNewPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MyPageRepository {
    suspend fun getLikePost(userEmail: String): ResponseLikePost
    suspend fun getNewPost(userEmail: String): ResponseNewPost
}