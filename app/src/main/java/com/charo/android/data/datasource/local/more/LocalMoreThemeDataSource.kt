package com.charo.android.data.datasource.local.more

import com.charo.android.presentation.ui.more.MoreThemeInfo

// νμΈμλ§
interface LocalMoreThemeDataSource {
    fun fetchData() : MutableList<MoreThemeInfo>
}