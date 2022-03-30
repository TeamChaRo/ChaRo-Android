package com.charo.android.domain.usecase.mypage

import com.charo.android.data.mapper.MyPageMapper
import com.charo.android.data.model.mypage.PostInfo
import com.charo.android.domain.repository.mypage.MyPageRepository

class GetRemoteMoreSavedLikePostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String, lastId: Int, lastCount: Int): PostInfo {
        return MyPageMapper.mapperToMoreSavedLikePost(
            repository.getMoreSavedLikePost(
                userEmail,
                lastId,
                lastCount
            )
        )
    }
}