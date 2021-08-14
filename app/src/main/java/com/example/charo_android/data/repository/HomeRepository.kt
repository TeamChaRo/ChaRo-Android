package com.example.charo_android.data.repository

import androidx.datastore.preferences.protobuf.Api
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.response.ResponseHomeViewData

interface HomeRepository {
    suspend fun getMain(userId : String) : ResponseHomeViewData
}