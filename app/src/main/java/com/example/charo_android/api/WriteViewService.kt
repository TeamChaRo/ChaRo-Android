package com.example.charo_android.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/writePost")
    fun postWrite(
    //    @Part body: RequestWriteData,
        @Part title: MultipartBody.Part,
        @Part userId: MultipartBody.Part,
        @Part province: MultipartBody.Part,
        @Part region: MultipartBody.Part,
        @Part warning: MultipartBody.Part,
        @Part theme: MultipartBody.Part,
        @Part isParking: MultipartBody.Part,
        @Part parkingDesc: MultipartBody.Part,
        @Part courseDesc: MultipartBody.Part,
        @Part course: MultipartBody.Part,
        @Part image: MultipartBody.Part
        ): Call<ResponseWriteData>
}