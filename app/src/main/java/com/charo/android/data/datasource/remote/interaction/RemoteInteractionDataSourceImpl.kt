package com.charo.android.data.datasource.remote.interaction

import com.example.charo_android.data.api.interaction.InteractionService
import com.example.charo_android.data.model.interaction.RequestInteractionData
import com.example.charo_android.data.model.interaction.ResponseInteractionData

class RemoteInteractionDataSourceImpl(private val service: InteractionService): RemoteInteractionDataSource {
    override suspend fun postLike(requestInteractionData: RequestInteractionData): ResponseInteractionData {
        return service.postLike(requestInteractionData)
    }

    override suspend fun postSave(requestInteractionData: RequestInteractionData): ResponseInteractionData {
        return service.postSave(requestInteractionData)
    }
}