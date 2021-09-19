package com.example.charo_android.domain.repository

import com.example.charo_android.data.model.entity.*
import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.domain.model.home.*

interface HomeRepository {
    suspend fun getMain(userEmail : String) : ResponseHomeViewData


}