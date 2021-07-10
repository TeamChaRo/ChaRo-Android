package com.example.charo_android.data

import com.example.charo_android.ui.charo.MyCharoInfo

interface MyCharoDataSource {
    fun fetchData(): MutableList<MyCharoInfo>
}