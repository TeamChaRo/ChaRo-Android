package com.example.charo_android.domain.usecase.mypage

import com.example.charo_android.data.mapper.MyPageMapper
import com.example.charo_android.data.model.mypage.PostInfo
import com.example.charo_android.domain.repository.mypage.MyPageRepository

class GetRemoteLikePostUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String): PostInfo {
        return MyPageMapper.mapperToWrittenLikePost(repository.getLikePost(userEmail))
    }
}