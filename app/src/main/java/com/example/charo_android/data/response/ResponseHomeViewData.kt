package com.example.charo_android.data.response

data class ResponseHomeViewData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val banner: List<Banner>,
        val customThemeDrive: List<CustomThemeDrive>,
        val customThemeTitle: String,
        val localDrive: List<LocalDrive>,
        val localTitle: String,
        val todayCharoDrive: List<TodayCharoDrive>,
        val trendDrive: List<TrendDrive>
    ) {
        data class Banner(
            val bannerImage: String,
            val bannerTag: String,
            val bannerTitle: String
        )

        data class LocalDrive(
            val image: String,
            val isFavorite: Boolean,
            val postId: Int,
            val tags: List<String>,
            val title: String
        )

        data class CustomThemeDrive(
            val image: String,
            val isFavorite: Boolean,
            val postId: Int,
            val tags: List<String>,
            val title: String
        )

        data class TodayCharoDrive(
            val image: String,
            val isFavorite: Boolean,
            val postId: Int,
            val tags: List<String>,
            val title: String
        )

        data class TrendDrive(
            val image: String,
            val isFavorite: Boolean,
            val postId: Int,
            val tags: List<String>,
            val title: String
        )
    }
}