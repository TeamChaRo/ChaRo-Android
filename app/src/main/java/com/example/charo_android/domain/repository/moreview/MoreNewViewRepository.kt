package com.example.charo_android.domain.repository.moreview

import com.example.charo_android.data.model.response.more.ResponseMoreViewData

interface MoreNewViewRepository {
    suspend fun getMoreNewView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}