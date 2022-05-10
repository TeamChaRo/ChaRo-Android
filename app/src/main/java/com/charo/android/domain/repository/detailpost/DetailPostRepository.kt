package com.charo.android.domain.repository.detailpost

import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.domain.model.detailpost.User

interface DetailPostRepository {
    suspend fun getDetailPost(userEmail: String, postId: Int): DetailPost
    suspend fun getDetailPostLikeUserList(postId: Int, userEmail: String): List<User>
    suspend fun deleteDetailPost(postId: Int): Boolean
}