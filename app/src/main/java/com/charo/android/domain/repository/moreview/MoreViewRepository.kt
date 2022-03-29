package com.charo.android.domain.repository.moreview

import com.example.charo_android.data.model.response.more.ResponseMoreViewData

interface MoreViewRepository {
    suspend fun getMoreView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}