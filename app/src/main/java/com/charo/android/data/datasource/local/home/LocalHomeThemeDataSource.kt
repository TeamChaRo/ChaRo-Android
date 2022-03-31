package com.charo.android.data.datasource.local.home

import com.charo.android.data.model.entity.HomeThemeInfo


interface LocalHomeThemeDataSource {
    fun fetchData() : MutableList<HomeThemeInfo>
    
}
