package com.charo.android.domain.repository.moreview

import com.charo.android.data.model.response.more.ResponseMoreViewData

interface MoreViewRepository {
    suspend fun getMoreView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}