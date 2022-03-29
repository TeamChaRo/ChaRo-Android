package com.charo.android.data.datasource.remote.more

import com.example.charo_android.data.model.response.more.ResponseMoreViewData

interface RemoteMoreNewViewDataSource {
    suspend fun getMoreNewView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}