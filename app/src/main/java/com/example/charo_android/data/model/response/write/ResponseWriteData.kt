package com.example.charo_android.data.model.response.write

data class ResponseWriteData(
    val success: Boolean,
    val msg: String,
    val data: ArrayList<Location>
){
    data class Location(
        val title: String,
        val address: String,
        val latitude: String,
        val longitude: String,
        val year: String,
        val month: String,
        val day: String
    )
}
