package com.charo.android.data.datasource.remote.detailpost

import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import com.example.charo_android.data.model.detailpost.ResponseDetailPostLikeUserList

interface RemoteDetailPostDataSource {
    suspend fun getDetailPost(userEmail: String, postId: Int): ResponseDetailPost
    suspend fun getDetailPostLikeUserList(postId: Int, userEmail: String): ResponseDetailPostLikeUserList
}