package com.charo.android.data.datasource.repositoryimpl.interaction

import com.example.charo_android.data.datasource.remote.interaction.RemoteInteractionDataSource
import com.example.charo_android.data.model.interaction.RequestInteractionData
import com.example.charo_android.domain.repository.interaction.InteractionRepository

class InteractionRepositoryImpl(private val dataSource: RemoteInteractionDataSource) :
    InteractionRepository {
    override suspend fun postLike(userEmail: String, postId: Int): Boolean {
        return dataSource.postLike(RequestInteractionData(userEmail, postId)).success
    }

    override suspend fun postSave(userEmail: String, postId: Int): Boolean {
        return dataSource.postSave(RequestInteractionData(userEmail, postId)).success
    }

}