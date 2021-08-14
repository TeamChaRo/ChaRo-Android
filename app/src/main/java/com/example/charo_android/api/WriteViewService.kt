package com.example.charo_android.api

import com.example.charo_android.data.request.RequestWriteData
import com.example.charo_android.data.response.ResponseWriteData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/writePost")
    fun writePost(
//        @PartMap request: RequestBody,
//        @Part course: RequestWriteData.Course,
//        @Part courseDesc: String,
//        @Part isParking: Boolean,
//        @Part parkingDesc: String,
//        @Part province: String,
//        @Part region: String,
//        @Part theme: List<String>,
//        @Part title: String,
//        @Part userId: String,
//        @Part warning: List<Boolean>,
        @Part course: RequestWriteData.Course,
        @Part courseDesc: String,
        @Part isParking: Boolean,
        @Part parkingDesc: String,
        @Part province: String,
        @Part region: String,
        @Part theme: List<String>,
        @Part title: String,
        @Part userId: String,
        @Part warning: List<Boolean>,
        @Part files: List<MultipartBody.Part>
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