package com.charo.android.domain.repository.interaction

interface InteractionRepository {
    suspend fun postLike(userEmail: String, postId: Int): Boolean
    suspend fun postSave(userEmail: String, postId: Int): Boolean
}