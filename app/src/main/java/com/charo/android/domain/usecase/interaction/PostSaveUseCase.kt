package com.charo.android.domain.usecase.interaction

import com.charo.android.domain.repository.interaction.InteractionRepository


class PostSaveUseCase(private val repository: InteractionRepository) {
    suspend operator fun invoke(userEmail: String, postId: Int): Boolean {
        return repository.postSave(userEmail, postId)
    }
}