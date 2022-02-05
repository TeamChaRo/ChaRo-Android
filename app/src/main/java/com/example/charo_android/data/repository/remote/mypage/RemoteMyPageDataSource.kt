package com.example.charo_android.data.repository.remote.mypage

import com.example.charo_android.data.model.mypage.ResponseEndlessScroll
import com.example.charo_android.data.model.mypage.ResponseMyPage

interface RemoteMyPageDataSource {
    suspend fun getLikePost(userEmail: String): ResponseMyPage

    suspend fun getNewPost(userEmail: String): ResponseMyPage

    suspend fun getMoreWrittenLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll

    suspend fun getMoreWrittenNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll

    suspend fun getMoreSavedLikePost(
        userEmail: String,
        lastId: Int,
        lastCount: Int
    ): ResponseEndlessScroll

    suspend fun getMoreSavedNewPost(
        userEmail: String,
        lastId: Int
    ): ResponseEndlessScroll
}