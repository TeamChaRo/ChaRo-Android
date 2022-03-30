package com.charo.android.domain.usecase.mypage

import com.charo.android.data.mapper.MyPageMapper
import com.charo.android.data.model.mypage.PostInfo
import com.charo.android.domain.repository.mypage.MyPageRepository

class GetRemoteMoreSavedNewPostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String, lastId: Int): PostInfo {
        return MyPageMapper.mapperToMoreSavedNewPost(
            repository.getMoreSavedNewPost(
                userEmail,
                lastId
            )
        )
    }
}