package com.charo.android.data.model.entity

import com.google.gson.annotations.SerializedName

data class HomeThemeInfo(
    @SerializedName("homeThemeImage")
    val homeThemeImage:Int,
    @SerializedName("homeThemeTitle")
    val homeThemeTitle :String)
