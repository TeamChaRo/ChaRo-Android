package com.charo.android.domain.usecase.detailpost

import com.charo.android.domain.model.detailpost.User
import com.charo.android.domain.repository.detailpost.DetailPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDetailPostLikeUserListUseCase(private val repository: DetailPostRepository) {
    suspend operator fun invoke(postId: Int, userEmail: String): List<User> {
        return withContext(Dispatchers.IO) {
            repository.getDetailPostLikeUserList(
                postId,
                userEmail
            )
        }
    }
}