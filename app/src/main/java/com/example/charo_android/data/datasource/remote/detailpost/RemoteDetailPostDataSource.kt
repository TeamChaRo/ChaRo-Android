package com.example.charo_android.data.datasource.remote.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost

interface RemoteDetailPostDataSource {
    suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost
}