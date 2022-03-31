package com.charo.android.domain.model.more

data class MoreDrive(
    val moreDay: String,
    val moreImage: String,
    val moreIsFavorite: Boolean,
    val moreMonth: String,
    val morePostId: Int,
    val moreRegion: String ?= "",
    val moreTheme: String ?= "",
    val moreTitle: String,
    val moreWarning: String ?= "",
    val moreYear: String
)
