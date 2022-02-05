package com.example.charo_android.domain.usecase.mypage

import com.example.charo_android.data.mapper.MyPageMapper
import com.example.charo_android.data.model.mypage.UserInformation
import com.example.charo_android.domain.repository.mypage.MyPageRepository

class GetRemoteUserInformationUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userEmail: String): UserInformation {
        return MyPageMapper.mapperToUserInformation(repository.getLikePost(userEmail))
    }
}