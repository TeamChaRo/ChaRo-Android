package com.example.charo_android.data.api

import com.example.charo_android.data.model.request.RequestDetailSaveData
import com.example.charo_android.data.model.response.ResponseDetailSaveData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DetailViewSaveService {
    @POST("/post/save")
    fun postDetailSave(
        @Body body: RequestDetailSaveData
    ): Call<ResponseDetailSaveData>
}