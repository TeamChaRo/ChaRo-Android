package com.charo.android.domain.usecase.detailpost

import com.charo.android.domain.repository.detailpost.DetailPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteDetailPostUseCase(private val repository: DetailPostRepository) {
    suspend operator fun invoke(postId: Int, images: List<String>): Boolean {
        return withContext(Dispatchers.IO) {
            repository.deleteDetailPost(postId, images)
        }
    }
}