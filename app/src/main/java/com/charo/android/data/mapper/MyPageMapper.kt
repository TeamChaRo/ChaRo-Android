package com.charo.android.data.mapper

import com.example.charo_android.data.model.mypage.*

object MyPageMapper {
    fun mapperToLikePost(responseMyPage: ResponseMyPage): ResponseMyPage.Data {
        return responseMyPage.data
    }

    fun mapperToNewPost(responseMyPage: ResponseMyPage): ResponseMyPage.Data {
        return responseMyPage.data
    }

    fun mapperToMoreWrittenLikePost(responseEndlessScroll: ResponseEndlessScroll): PostInfo {
        return responseEndlessScroll.data
    }

    fun mapperToMoreWrittenNewPost(responseEndlessScroll: ResponseEndlessScroll): PostInfo {
        return responseEndlessScroll.data
    }

    fun mapperToMoreSavedLikePost(responseEndlessScroll: ResponseEndlessScroll): PostInfo {
        return responseEndlessScroll.data
    }

    fun mapperToMoreSavedNewPost(responseEndlessScroll: ResponseEndlessScroll): PostInfo {
        return responseEndlessScroll.data
    }
}