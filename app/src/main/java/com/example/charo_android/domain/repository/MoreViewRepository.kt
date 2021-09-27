package com.example.charo_android.domain.repository

import com.example.charo_android.data.model.response.more.ResponseMoreViewData

interface MoreViewRepository {
    suspend fun getMoreView(userEmail:String, identifer: String, value : String) : ResponseMoreViewData
}