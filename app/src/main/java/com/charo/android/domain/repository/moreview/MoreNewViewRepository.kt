package com.charo.android.domain.repository.moreview

import com.charo.android.data.model.response.more.ResponseMoreViewData
import kotlinx.coroutines.flow.Flow


interface MoreNewViewRepository {
    fun getMoreNewView(userEmail:String, identifer: String, value : String) : Flow<ResponseMoreViewData>
}