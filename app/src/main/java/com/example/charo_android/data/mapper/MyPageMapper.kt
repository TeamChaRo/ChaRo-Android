package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.mypage.*

object MyPageMapper {
    fun mapperToUserInformation(responseMyPage: ResponseMyPage): UserInformation {
        return responseMyPage.data.userInformation
    }

    fun mapperToWrittenLikePost(responseMyPage: ResponseMyPage): PostInfo {
        return responseMyPage.data.writtenPost
    }

    fun mapperToSavedLikePost(responseMyPage: ResponseMyPage): PostInfo {
        return responseMyPage.data.savedPost
    }

    fun mapperToWrittenNewPost(responseMyPage: ResponseMyPage): PostInfo {
        return responseMyPage.data.writtenPost
    }

    fun mapperToSavedNewPost(responseMyPage: ResponseMyPage): PostInfo {
        return responseMyPage.data.savedPost
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