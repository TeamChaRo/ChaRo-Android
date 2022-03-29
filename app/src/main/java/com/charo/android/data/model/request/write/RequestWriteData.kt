package com.charo.android.data.model.request.write

import com.google.gson.annotations.SerializedName

 class RequestWriteData(
     @SerializedName("course")
    val course: ArrayList<Course>,
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
    data class Course(
        val address : String,
        val longitude : String,
        val latitude : String,
    )
}