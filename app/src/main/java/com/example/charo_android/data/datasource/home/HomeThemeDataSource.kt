package com.example.charo_android.data.datasource.home

import com.example.charo_android.ui.home.model.HomeThemeInfo

interface HomeThemeDataSource {
    fun fetchData() : MutableList<HomeThemeInfo>
}