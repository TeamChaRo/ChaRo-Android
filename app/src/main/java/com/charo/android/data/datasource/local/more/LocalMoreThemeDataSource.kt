package com.charo.android.data.datasource.local.more

import com.example.charo_android.presentation.ui.more.MoreThemeInfo

interface LocalMoreThemeDataSource {
    fun fetchData() : MutableList<MoreThemeInfo>
}