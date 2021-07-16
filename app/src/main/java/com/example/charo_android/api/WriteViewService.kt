package com.example.charo_android.api

import com.example.charo_android.data.ResponseWriteData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/writePost")
    fun writePost(
        @PartMap request: HashMap<String, RequestBody>,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseWriteData>
}