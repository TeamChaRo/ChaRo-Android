package com.charo.android.data.model.response.write

import com.google.gson.annotations.SerializedName

data class ResponseWriteData(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: ArrayList<Location>
){
    data class Location(
        @SerializedName("title")
        val title: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("year")
        val year: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("day")
        val day: String
    )
}
