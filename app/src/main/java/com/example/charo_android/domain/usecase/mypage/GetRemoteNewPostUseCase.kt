package com.example.charo_android.domain.usecase.mypage

import com.example.charo_android.data.mapper.MyPageMapper
import com.example.charo_android.data.model.mypage.ResponseMyPage
import com.example.charo_android.domain.repository.mypage.MyPageRepository

class GetRemoteNewPostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String): ResponseMyPage.Data {
        return MyPageMapper.mapperToNewPost(repository.getNewPost(userEmail))
    }
}