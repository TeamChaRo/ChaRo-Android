package com.charo.android.data.mapper

import com.charo.android.data.model.mypage.PostInfo
import com.charo.android.data.model.mypage.ResponseEndlessScroll
import com.charo.android.data.model.mypage.ResponseMyPage


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