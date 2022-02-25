package com.example.charo_android.domain.usecase.detailpost

import com.example.charo_android.data.mapper.DetailPostMapper
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.repository.detailpost.DetailPostRepository

class GetRemoteDetailPostUseCase(private val repository: DetailPostRepository) {
    suspend operator fun invoke(userEmail: String, postId: Int): DetailPost {
        return DetailPostMapper.mapperToDetailPost(repository.getDetailPost(userEmail, postId))
    }
}