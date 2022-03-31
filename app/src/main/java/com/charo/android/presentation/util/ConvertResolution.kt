package com.charo.android.presentation.util

import android.util.DisplayMetrics

object ConvertResolution {
    fun dpToPx(dp: Int): Int {
        return dp * DisplayMetrics().densityDpi
    }

    fun pxToDp(px: Int): Int {
        return px / DisplayMetrics().densityDpi
    }
}