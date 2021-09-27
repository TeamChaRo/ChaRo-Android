package com.example.charo_android.data.model.response

data class ResponseSearchViewData(
    val data: Data,
    val msg: String,
    val success: Boolean
){
    data class Data(
        val drive: List<Drive>,
        val totalCourse: Int
    ){
        data class Drive(
            val image: String,
            val isFavorite: Boolean,
            val postId: Int,
            val tags: List<String>,
            val title: String
        )
    }
}