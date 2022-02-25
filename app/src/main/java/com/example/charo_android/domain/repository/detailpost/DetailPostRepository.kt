package com.example.charo_android.domain.repository.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost

interface DetailPostRepository {
    suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost
}