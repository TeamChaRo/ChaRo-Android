package com.charo.android.domain.usecase.mypage

import com.charo.android.domain.repository.mypage.MyPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRemoteFollowUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String, targetEmail: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.getFollow(userEmail, targetEmail)
        }
    }
}