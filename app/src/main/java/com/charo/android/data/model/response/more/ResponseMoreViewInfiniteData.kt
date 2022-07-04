package com.charo.android.data.model.response.more

import com.google.gson.annotations.SerializedName

data class ResponseMoreViewInfiniteData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("drive")
        val drive: List<Drive>,
        @SerializedName("lastCount")
        val lastCount: Int,
        @SerializedName("lastId")
        val lastId: Int
    ) {
        data class Drive(
            @SerializedName("day")
            val day: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("isFavorite")
            val isFavorite: Boolean,
            @SerializedName("month")
            val month: String,
            @SerializedName("postId")
            val postId: Int,
            @SerializedName("region")
            val region: String,
            @SerializedName("theme")
            val theme: String,
            @SerializedName("title")
            val title: String,
            @SerializedName("warning")
            val warning: String,
            @SerializedName("year")
            val year: String
        )
    }
}