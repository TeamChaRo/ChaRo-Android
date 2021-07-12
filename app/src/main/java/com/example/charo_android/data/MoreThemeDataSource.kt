package com.example.charo_android.data

import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.ui.home.more.MoreThemeInfo

interface MoreThemeDataSource {
    fun fetchData() : MutableList<MoreThemeInfo>
}