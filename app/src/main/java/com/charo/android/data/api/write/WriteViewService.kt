package com.charo.android.data.api.write


import com.charo.android.data.model.request.write.RequestWriteHistoryData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.write.ResponseWriteData
import com.charo.android.data.model.write.ResponseEditData

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/post/write")
    fun writePost(
        @PartMap warning: HashMap<String, RequestBody>,
        @PartMap theme: HashMap<String, RequestBody>,
        @Part course: ArrayList<MultipartBody.Part>,
        @Part image: ArrayList<MultipartBody.Part>,
        @PartMap stringData: HashMap<String, RequestBody>,
        ): Call<ResponseStatusCode>

    @GET("/post/readHistory/{userEmail}")
    fun getReadHistory(
        @Path("userEmail") userEmail: String
    ): Call<ResponseWriteData>

    @POST("/post/saveHistory")
    fun postSaveHistory(
        @Body body: RequestWriteHistoryData
    ): Call<ResponseStatusCode>

    @Multipart
    @PUT("/post")
    fun editPost(
//        @Part postId: MultipartBody,
        @PartMap warning: HashMap<String, RequestBody>,
        @PartMap theme: HashMap<String, RequestBody>,
        @Part course: ArrayList<MultipartBody.Part>,
        @Part image: ArrayList<MultipartBody.Part>,
        @PartMap stringData: HashMap<String, RequestBody>
//        @Part deleted: ArrayList<MultipartBody.Part>
    ): Call<ResponseEditData>
}