package com.charo.android.data.model.write

import android.text.SpannableString

data class MapSearchInfo(
    val locationName: SpannableString,
    val locationAddress: String,
    val date: String,
)