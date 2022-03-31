package com.charo.android.data.model.response.more

data class ResponseMoreViewData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val drive: List<Drive>,
        val lastCount: Int,
        val lastId: Int
    ) {
        data class Drive(
            val day: String,
            val image: String,
            val isFavorite: Boolean,
            val month: String,
            val postId: Int,
            val region: String,
            val theme: String,
            val title: String,
            val warning: String ,
            val year: String
        )
    }
}