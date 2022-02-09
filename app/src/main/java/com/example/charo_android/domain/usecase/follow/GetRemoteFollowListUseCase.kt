package com.example.charo_android.domain.usecase.follow

import com.example.charo_android.data.model.mypage.ResponseFollowList
import com.example.charo_android.domain.repository.follow.FollowRepository

class GetRemoteFollowListUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(userEmail: String, myPageEmail: String): ResponseFollowList.Data {
        // Mapper 미사용
        return repository.getFollowList(userEmail, myPageEmail).data
    }
}