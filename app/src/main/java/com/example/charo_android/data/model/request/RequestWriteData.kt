package com.example.charo_android.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestWriteData(
    @SerializedName("course")
    val course: Course,
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
    val theme: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("warning")
    val warning: List<Boolean>
) {
    data class Course(
        @SerializedName("address")
        val address: List<String>,
        @SerializedName("latitude")
        val latitude: List<String>,
        @SerializedName("longtitude")
        val longtitude: List<String>
    )
}