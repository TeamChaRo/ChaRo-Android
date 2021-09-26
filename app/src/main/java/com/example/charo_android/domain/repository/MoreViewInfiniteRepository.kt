package com.example.charo_android.domain.repository

import com.example.charo_android.data.model.response.more.ResponseMoreViewInfiniteData

interface MoreViewInfiniteRepository {
    suspend fun getPreview(userId:String, identifer: String, postId : Int, count: Int, value : String)
            : ResponseMoreViewInfiniteData
}