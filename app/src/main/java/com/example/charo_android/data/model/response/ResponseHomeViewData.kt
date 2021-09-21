package com.example.charo_android.data.model.response

data class ResponseHomeViewData(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val banner: List<Banner>,
        val customDrive: CustomDrive,
        val customTitle: String,
        val localDrive: LocalDrive,
        val localTitle: String,
        val todayCharoDrive: TodayCharoDrive,
        val trendDrive: TrendDrive
    ) {
        data class Banner(
            val bannerImage: String,
            val bannerTag: String,
            val bannerTitle: String
        )

        data class CustomDrive(
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
                val warning: String,
                val year: String
            )
        }

        data class LocalDrive(
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
                val warning: String,
                val year: String
            )
        }

        data class TodayCharoDrive(
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
                val warning: String,
                val year: String
            )
        }

        data class TrendDrive(
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
                val warning: String,
                val year: String
            )
        }
    }
}