package com.example.charo_android.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestWriteData(
    @SerializedName("course")
    val course: Any,
    @SerializedName("courseDesc")
    val courseDesc: String,
    @SerializedName("isParking")
    val isParking: Boolean,
    @SerializedName("parkingDesc")
    val parkingDesc: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("theme")
    val theme: ArrayList<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("warning")
    val warning: ArrayList<String>
) {
    object Course{
        val startCourse = mapOf<String,String>("address" to "", "latitude" to "", "longtitude" to "")
        val middleCourse = mapOf<String,String>("address" to "", "latitude" to "", "longtitude" to "")
        val endCourse = mapOf<String,String>("address" to "", "latitude" to "", "longtitude" to "")



    }
//        @SerializedName("address")
//        val address: List<String>,
//        @SerializedName("latitude")
//        val latitude: List<String>,
//        @SerializedName("longtitude")
//        val longtitude: List<String>

}