package com.charo.android.domain.model.detailpost

data class DetailPost(
    // 추가부분
    val region: String,
    val title: String,
    val createdAt: String,
    // 여기까지 추가된 부분
    val images: List<String>,
    val province: String,
    val isParking: Boolean,
    val parkingDesc: String?,
    val courseDesc: String,
    val themes: List<String>,
    val warnings: List<Boolean>,
    val author: String,
    val authorEmail: String,
    val isAuthor: Boolean,
    val profileImage: String,
    val likesCount: Int,
    var isFavorite: Int,
    var isStored: Int,
    val course: List<Course>
) {
    data class Course(
        val address: String,
        val latitude: Double,
        val longitude: Double
    )
}