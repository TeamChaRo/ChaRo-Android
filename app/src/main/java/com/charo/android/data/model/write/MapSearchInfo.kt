package com.charo.android.data.model.write

import android.text.SpannableString
import com.google.gson.annotations.SerializedName

data class MapSearchInfo(
    @SerializedName("locationName")
    val locationName: SpannableString,
    @SerializedName("locationAddress")
    val locationAddress: String,
    @SerializedName("date")
    val date: String,
)