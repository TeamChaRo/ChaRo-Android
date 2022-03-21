package com.example.charo_android.domain.repository.detailpost

import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.model.detailpost.User

interface DetailPostRepository {
    suspend fun getDetailPost(userEmail: String, postId: Int): DetailPost
    suspend fun getDetailPostLikeUserList(postId: Int, userEmail: String): List<User>
}