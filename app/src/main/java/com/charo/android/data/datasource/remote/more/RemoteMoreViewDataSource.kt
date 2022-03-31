package com.charo.android.data.datasource.remote.more

import com.charo.android.data.model.response.more.ResponseMoreViewData


interface RemoteMoreViewDataSource {
    suspend fun getMoreView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}