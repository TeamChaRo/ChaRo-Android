package com.charo.android.domain.usecase.detailpost

import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.domain.repository.detailpost.DetailPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDetailPostUseCase(private val repository: DetailPostRepository) {
    suspend operator fun invoke(userEmail: String, postId: Int): DetailPost {
        return withContext(Dispatchers.IO) {
            repository.getDetailPost(userEmail, postId)
        }
    }
}