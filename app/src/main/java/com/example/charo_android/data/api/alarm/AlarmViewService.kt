package com.example.charo_android.data.api.alarm

import com.example.charo_android.data.model.response.alarm.ResponseAlarmDeleteData
import com.example.charo_android.data.model.response.alarm.ResponseAlarmListData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlarmViewService {
    @GET("/push/{userEmail}")
    fun getAlarmList(
        @Path("userEmail") userEmail : String
    ) : Call<ResponseAlarmListData>

    @POST("/push/remove/{pushId}")
    fun postDeleteAlarm(
        @Path("pushId") pushId : Int
    ) : Call<ResponseAlarmDeleteData>
}