package com.example.charo_android.data.model.detail

data class ResponseDetailData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val author: String,
        val course: List<Course>,
        val courseDesc: String,
        val images: List<String>,
        val isAuthor: Boolean,
        val isFavorite: Int,
        val isParking: Boolean,
        val isStored: Int,
        val likesCount: Int,
        val parkingDesc: String,
        val profileImage: String,
        val province: String,
        val themes: List<String>,
        val warnings: List<Boolean>
    ) {
        data class Course(
            val address: String,
            val latitude: String,
            val longitude: String
        )
    }
}