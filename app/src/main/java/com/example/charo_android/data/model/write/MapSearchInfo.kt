package com.example.charo_android.data.model.write

import android.text.SpannableString

data class MapSearchInfo(
    val locationName: SpannableString,
    val locationAddress: String,
    val date: String,
)