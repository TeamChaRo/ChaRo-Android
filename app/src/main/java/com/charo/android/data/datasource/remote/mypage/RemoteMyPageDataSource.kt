package com.charo.android.data.datasource.remote.mypage

import com.charo.android.data.model.mypage.ResponseEndlessScroll
import com.charo.android.data.model.mypage.ResponseFollow
import com.charo.android.data.model.mypage.ResponseMyPage


interface RemoteMyPageDataSource {
    suspend fun getLikePost(userEmail: String): ResponseMyPage

    suspend fun getNewPost(userEmail: String): ResponseMyPage

    suspend fun getFollow(userEmail: String, targetEmail: String): ResponseFollow

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