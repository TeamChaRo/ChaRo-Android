package com.example.charo_android.data.model.entity

import com.google.gson.annotations.SerializedName

data class BannerEntity(
    @SerializedName("bannerImage")
    val homeViewPagerRoadImage : String,

    @SerializedName("bannerTag")
    val homeViewPagerTag : String,

    @SerializedName("bannerTitle")
    val homeViewPagerTitle : String
)
