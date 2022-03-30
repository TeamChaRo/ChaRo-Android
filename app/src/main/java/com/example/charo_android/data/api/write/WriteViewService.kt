package com.example.charo_android.data.api.write

import com.example.charo_android.data.model.request.write.RequestWriteHistoryData
import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.write.ResponseWriteData
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
        @PartMap course: HashMap<String, RequestBody>,
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
}