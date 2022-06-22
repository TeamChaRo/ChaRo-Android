package com.charo.android.data.datasource.local.more

import com.charo.android.presentation.ui.more.MoreThemeInfo

// 확인요망
interface LocalMoreThemeDataSource {
    fun fetchData() : MutableList<MoreThemeInfo>
}