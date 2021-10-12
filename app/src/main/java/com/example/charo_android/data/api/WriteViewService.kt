package com.example.charo_android.data.api

import com.example.charo_android.data.model.request.RequestWriteData
import com.example.charo_android.data.model.response.ResponseWriteData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/post/write")
    fun writePost(
        @Part("userEmail") userEmail: RequestBody, //String
        @Part("title") title: RequestBody,  //String
        @Part("province") province: RequestBody,  //String
        @Part("region") region: RequestBody,  //String
        @Part warning: ArrayList<MultipartBody.Part>?,  //ArrayList<String>?
        @Part theme: ArrayList<MultipartBody.Part>?,  //ArrayList<String>?
        @Part("isParking") isParking: Boolean?,  //Boolean
        @Part("parkingDesc") parkingDesc: RequestBody,  //String
        @Part("courseDesc") courseDesc: RequestBody,  //String
        @Part("course") course: ArrayList<HashMap<String, String>>?, //RequestWriteData.Course
        @Part image: ArrayList<MultipartBody.Part>?
    ): Call<ResponseWriteData>
}

//data class RequestWriteData(
//    @SerializedName("course")
//    val course: Course,
//    @SerializedName("courseDesc")
//    val courseDesc: String,
//    @SerializedName("isParking")
//    val isParking: Boolean,
//    @SerializedName("parkingDesc")
//    val parkingDesc: String,
//    @SerializedName("province")
//    val province: String,
//    @SerializedName("region")
//    val region: String,
//    @SerializedName("theme")
//    val theme: List<String>,
//    @SerializedName("title")
//    val title: String,
//    @SerializedName("userId")
//    val userId: String,
//    @SerializedName("warning")
//    val warning: List<Boolean>
//) {
//    data class Course(
//        @SerializedName("address")
//        val address: List<String>,
//        @SerializedName("latitude")
//        val latitude: List<String>,
//        @SerializedName("longtitude")
//        val longtitude: List<String>
//    )
//}