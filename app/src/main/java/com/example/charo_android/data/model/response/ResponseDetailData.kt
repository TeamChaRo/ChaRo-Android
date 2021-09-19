package com.example.charo_android.data.model.response

data class ResponseDetailData(
    val msg: String,
    val success: Boolean,
    val data: List<Data>?
) {
    data class Data(
        val author: String,
        val isAuthor: Boolean,
        val city: String,
        val courseDesc: String,
        val destination: String,
        val images: List<String>,
        val isFavorite: Boolean,
        val isParking: Boolean,
        val isStored: Boolean,
        val latitude: List<String>,
        val likesCount: Int,
        val longtitude: List<String>,
        val parkingDesc: String,
        val postingDay: String,
        val postingMonth: String,
        val postingYear: String,
        val profileImage: String,
        val province: String,
        val source: String,
        val themes: List<String>,
        val title: String,
        val warnings: List<Boolean>,
        val wayPoint: List<String>
    )
}