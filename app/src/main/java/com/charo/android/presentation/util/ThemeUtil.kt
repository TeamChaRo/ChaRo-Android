package com.charo.android.presentation.util


class ThemeUtil {
    val themeMap = hashMapOf(
        "선택안함" to "",
        "봄" to "spring",
        "여름" to "summer",
        "가을" to "fall",
        "겨울" to "winter",
        "산" to "mountain",
        "바다" to "sea",
        "호수" to "lake",
        "강" to "river",
        "해안도로" to "oceanRoad",
        "벚꽃" to "blossom",
        "단풍" to "maple",
        "여유" to "relax",
        "스피드" to "speed",
        "야경" to "nightView",
        "도심" to "cityView",
        "선택안함" to "nothing"
    )

    val cautionMap = hashMapOf(
        "선택안함" to "",
        "고속도로" to "highway",
        "산길포함" to "mountainRoad",
        "초보힘듦" to "diffRoad",
        "사람많음" to "hotPlace"
    )

    val itemCaution = arrayOf("선택안함", "고속도로", "산길포함", "초보힘듦", "사람많음")

    val itemTheme = arrayOf("봄","여름","가을","겨울","산","바다","호수","강","해안도로","벚꽃","단풍",
    "여유","스피드","야경","도심")
}
