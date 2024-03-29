package com.charo.android.data.api

import com.charo.android.BuildConfig
import com.charo.android.data.api.alarm.AlarmViewService
import com.charo.android.data.api.write.WriteViewService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val writeViewService: WriteViewService = retrofit.create(WriteViewService::class.java)
    val alarmViewService: AlarmViewService = retrofit.create(AlarmViewService::class.java)
}