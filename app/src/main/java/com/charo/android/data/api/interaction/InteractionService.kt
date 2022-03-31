package com.charo.android.data.api.interaction


import com.charo.android.data.model.interaction.RequestInteractionData
import com.charo.android.data.model.interaction.ResponseInteractionData
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