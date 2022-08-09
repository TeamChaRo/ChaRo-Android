package com.charo.android.domain.repository.moreview

import com.charo.android.data.model.response.more.ResponseMoreViewData
import com.charo.android.domain.model.more.MoreDrive
import kotlinx.coroutines.flow.Flow

interface MoreViewRepository {
    fun getMoreView(userEmail:String, identifer: String, value : String) : Flow<ResponseMoreViewData>
}