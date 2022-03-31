package com.charo.android.domain.repository.moreview

import com.charo.android.data.model.response.more.ResponseMoreViewData


interface MoreNewViewRepository {
    suspend fun getMoreNewView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}