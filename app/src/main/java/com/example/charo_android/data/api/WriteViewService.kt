package com.example.charo_android.data.api

import com.example.charo_android.data.model.request.RequestWriteData
import com.example.charo_android.data.model.response.ResponseWriteData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WriteViewService {
    @Multipart
    @POST("/post/write")
    fun writePost(
        @Part("course") course: RequestWriteData.Course,
        @Part("courseDesc") courseDesc: String,
        @Part("isParking") isParking: Boolean,
        @Part("parkingDesc") parkingDesc: String,
        @Part("province") province: String,
        @Part("region") region: String,
        @Part("theme") theme: List<String>,
        @Part("title") title: String,
        @Part("userEmail") userEmail: String,
        @Part("warning") warning: List<Boolean>,
        @Part image: List<MultipartBody.Part>
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