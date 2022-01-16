package com.example.charo_android.data.api.alarm

import com.example.charo_android.data.model.response.alarm.ResponseAlarmListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AlarmViewService {
    @GET("/push/{userEmail}")
    suspend fun getAlarmList(
        @Path("userEmail") userEmail : String
    ) : Call<ResponseAlarmListData>
}