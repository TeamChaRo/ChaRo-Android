package com.charo.android.data.model.detailold

data class ResponseDetailData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val author: String,
        val course: List<Course>,
        val courseDesc: String,
        val images: MutableList<String>,
        val isAuthor: Boolean,
        var isFavorite: Int,
        val isParking: Boolean,
        val isStored: Int,
        var likesCount: Int,
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