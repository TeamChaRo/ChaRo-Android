package com.example.charo_android.data.model.detailpost

data class ResponseDetailPost(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val images: List<String>,
        val province: String,
        val isParking: Boolean,
        val parkingDesc: String,
        val courseDesc: String,
        val themes: List<String>,
        val warnings: List<Boolean>,
        val author: String,
        val authorEmail: String,
        val isAuthor: Boolean,
        val profileImage: String,
        val likesCount: Int,
        val isFavorite: Int,
        val isStored: Int,
        val course: List<Course>
    ) {
        data class Course(
            val address: String,
            val latitude: Double,
            val longitude: Double
        )
    }
}
