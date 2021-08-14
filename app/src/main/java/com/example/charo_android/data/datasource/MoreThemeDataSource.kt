package com.example.charo_android.data.datasource

import com.example.charo_android.ui.more.MoreThemeInfo

interface MoreThemeDataSource {
    fun fetchData() : MutableList<MoreThemeInfo>
}