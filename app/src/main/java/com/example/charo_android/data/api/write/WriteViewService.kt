package com.example.charo_android.data.api.write

import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.ResponseWriteData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/post/write")
    fun writePost(
//        @Part("userEmail") userEmail: RequestBody, //String
//        @Part("title") title: RequestBody,  //String
//        @Part("province") province: RequestBody,  //String
//        @Part("region") region: RequestBody,  //String
        @Part warning: ArrayList<MultipartBody.Part>?,  //ArrayList<String>?
        @Part theme: ArrayList<MultipartBody.Part>?,  //ArrayList<MultipartBody.Part>?
        @Part("isParking") isParking: Boolean?,  //Boolean
//        @Part("parkingDesc") parkingDesc: RequestBody,  //String
//        @Part("courseDesc") courseDesc: RequestBody,  //String
        @Part("course") course: ArrayList<HashMap<String, String>>?, //RequestWriteData.Course
        @Part image: ArrayList<MultipartBody.Part>?,
        @PartMap stringData : HashMap<String, RequestBody>,
//        @PartMap courseData : HashMap<String, ArrayList<HashMap<String,RequestBody>>>
    ): Call<ResponseStatusCode>

    @GET("/post/readHistory/{userEmail}")
    fun getReadHistory(
        @Path("userEmail") userEmail: String
    ): Call<ResponseWriteData>

}