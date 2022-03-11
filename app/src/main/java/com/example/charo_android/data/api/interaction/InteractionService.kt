package com.example.charo_android.data.api.interaction

import com.example.charo_android.data.model.interaction.RequestInteractionData
import com.example.charo_android.data.model.interaction.ResponseInteractionData
import retrofit2.http.Body
import retrofit2.http.POST

interface InteractionService {
    @POST("post/like")
    suspend fun postLike(
        @Body body: RequestInteractionData
    ): ResponseInteractionData

    @POST("post/save")
    suspend fun postSave(
        @Body body: RequestInteractionData
    ): ResponseInteractionData
}