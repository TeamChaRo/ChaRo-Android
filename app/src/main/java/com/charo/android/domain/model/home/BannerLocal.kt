package com.charo.android.domain.model.home

import android.graphics.fonts.Font

data class BannerLocal(
    val homeViewPagerRoadImage: Int,
    val homeViewPagerTitle: String,
    val homeViewPagerSubTitle: String,
    val homeViewPagerSubTitleImg: Int,
    val titleFontSize: Float,
    val charoImgVisible: Boolean
)