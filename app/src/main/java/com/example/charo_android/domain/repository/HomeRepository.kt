package com.example.charo_android.domain.repository

import com.example.charo_android.data.model.response.home.ResponseHomeViewData

interface HomeRepository {
    suspend fun getMain(userEmail : String) : ResponseHomeViewData


}