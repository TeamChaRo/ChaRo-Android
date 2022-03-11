package com.example.charo_android.data.datasource.remote.interaction

import com.example.charo_android.data.model.interaction.RequestInteractionData
import com.example.charo_android.data.model.interaction.ResponseInteractionData

interface RemoteInteractionDataSource {
    suspend fun postLike(requestInteractionData: RequestInteractionData): ResponseInteractionData
    suspend fun postSave(requestInteractionData: RequestInteractionData): ResponseInteractionData
}