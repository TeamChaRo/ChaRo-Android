package com.charo.android.domain.usecase.follow

import com.charo.android.domain.repository.follow.FollowRepository


class PostFollowUseCase(private val repository: FollowRepository) {
    suspend operator fun invoke(follower: String, followed: String): Boolean {
        return repository.postFollow(follower, followed)
    }
}