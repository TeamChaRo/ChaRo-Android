package com.example.charo_android.data.repository.local.home

import com.example.charo_android.data.model.entity.HomeThemeInfo

interface LocalHomeThemeDataSource {
    fun fetchData() : MutableList<HomeThemeInfo>
}
