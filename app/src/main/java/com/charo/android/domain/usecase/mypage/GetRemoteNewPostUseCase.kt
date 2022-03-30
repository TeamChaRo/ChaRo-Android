package com.charo.android.domain.usecase.mypage

import com.charo.android.data.mapper.MyPageMapper
import com.charo.android.data.model.mypage.ResponseMyPage
import com.charo.android.domain.repository.mypage.MyPageRepository

class GetRemoteNewPostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String): ResponseMyPage.Data {
        return MyPageMapper.mapperToNewPost(repository.getNewPost(userEmail))
    }
}