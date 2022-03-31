package com.charo.android.data.datasource.remote.interaction

import com.charo.android.data.model.interaction.RequestInteractionData
import com.charo.android.data.model.interaction.ResponseInteractionData


interface RemoteInteractionDataSource {
    suspend fun postLike(requestInteractionData: RequestInteractionData): ResponseInteractionData
    suspend fun postSave(requestInteractionData: RequestInteractionData): ResponseInteractionData
}