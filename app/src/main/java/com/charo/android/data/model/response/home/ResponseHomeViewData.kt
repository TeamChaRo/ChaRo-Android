package com.charo.android.data.model.response.home

import com.google.gson.annotations.SerializedName

data class ResponseHomeViewData(
    @SerializedName("data")
    val data: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("banner")
        val banner: List<Banner>,
        @SerializedName("customDrive")
        val customDrive: CustomDrive,
        @SerializedName("customTitle")
        val customTitle: String,
        @SerializedName("localDrive")
        val localDrive: LocalDrive,
        @SerializedName("localTitle")
        val localTitle: String,
        @SerializedName("todayCharoDrive")
        val todayCharoDrive: TodayCharoDrive,
        @SerializedName("trendDrive")
        val trendDrive: TrendDrive
    ) {
        data class Banner(
            @SerializedName("bannerImage")
            val bannerImage: String,
            @SerializedName("bannerTag")
            val bannerTag: String,
            @SerializedName("bannerTitle")
            val bannerTitle: String
        )

        data class CustomDrive(
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

        data class LocalDrive(
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

        data class TodayCharoDrive(
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

        data class TrendDrive(
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
}