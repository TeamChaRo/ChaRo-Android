package com.example.charo_android.domain.repository.home

import com.example.charo_android.data.model.response.home.ResponseHomeViewData

interface HomeRepository {
    suspend fun getMain(userEmail : String) : ResponseHomeViewData


}