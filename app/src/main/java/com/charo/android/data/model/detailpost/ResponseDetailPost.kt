package com.charo.android.data.model.detailpost

import com.google.gson.annotations.SerializedName

data class ResponseDetailPost(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        // 추가된 부분
        @SerializedName("postId")
        val postId: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("createdAt")
        val createdAt: String,
        // 여기까지
        @SerializedName("images")
        val images: List<String>,
        @SerializedName("province")
        val province: String,
        @SerializedName("isParking")
        val isParking: Boolean,
        @SerializedName("parkingDesc")
        val parkingDesc: String,
        @SerializedName("courseDesc")
        val courseDesc: String,
        @SerializedName("themes")
        val themes: List<String>,
        @SerializedName("warnings")
        val warnings: List<Boolean>,
        @SerializedName("author")
        val author: String,
        @SerializedName("authorEmail")
        val authorEmail: String,
        @SerializedName("isAuthor")
        val isAuthor: Boolean,
        @SerializedName("profileImage")
        val profileImage: String,
        @SerializedName("likesCount")
        val likesCount: Int,
        @SerializedName("isFavorite")
        val isFavorite: Int,
        @SerializedName("isStored")
        val isStored: Int,
        @SerializedName("course")
        val course: List<Course>
    ) {
        data class Course(
            @SerializedName("address")
            val address: String,
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("longitude")
            val longitude: Double
        )
    }
}
