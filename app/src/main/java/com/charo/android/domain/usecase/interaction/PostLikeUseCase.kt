package com.charo.android.domain.usecase.interaction

import com.example.charo_android.domain.repository.interaction.InteractionRepository

class PostLikeUseCase(private val repository: InteractionRepository) {
    suspend operator fun invoke(userEmail: String, postId: Int): Boolean {
        return repository.postLike(userEmail, postId)
    }
}