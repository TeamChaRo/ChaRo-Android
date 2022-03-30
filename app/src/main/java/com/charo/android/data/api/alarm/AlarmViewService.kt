package com.charo.android.data.api.alarm


import com.charo.android.data.model.request.alarm.RequestFcmData
import com.charo.android.data.model.request.alarm.RequestReadPushData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.alarm.ResponseAlarmDeleteData
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
import com.charo.android.data.model.response.alarm.ResponseFcmData
import retrofit2.Call
import retrofit2.http.Body
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

    @POST("/push/read")
    fun postReadAlarm(
        @Body pushId: RequestReadPushData
    ): Call<ResponseStatusCode>

    @POST("/push/fcm")
    fun pushFcm(
        @Body body: RequestFcmData
    ): Call<ResponseFcmData>
}