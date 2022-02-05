package com.example.charo_android.domain.usecase.mypage

import com.example.charo_android.data.mapper.MyPageMapper
import com.example.charo_android.data.model.mypage.PostInfo
import com.example.charo_android.domain.repository.mypage.MyPageRepository

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