package com.charo.android.data.datasource.remote.more

import com.charo.android.data.model.response.more.ResponseMoreViewData
import kotlinx.coroutines.flow.Flow


interface RemoteMoreViewDataSource {
     fun getMoreView(userEmail:String, identifer: String, value : String) : Flow<ResponseMoreViewData>
}