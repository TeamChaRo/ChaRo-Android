package com.charo.android.data.api

import com.charo.android.data.api.alarm.AlarmViewService
import com.charo.android.data.api.charo.MyPageViewFollowService
import com.charo.android.data.api.charo.MyPageViewLikeService
import com.charo.android.data.api.charo.MyPageViewMoreService
import com.charo.android.data.api.charo.MyPageViewNewService
import com.charo.android.data.api.detail.DetailViewService
import com.charo.android.data.api.write.WriteViewService
import com.charo.android.hidden.Hidden
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = Hidden.baseUrl

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val detailViewService: DetailViewService = retrofit.create(DetailViewService::class.java)
    val myPageViewLikeService = retrofit.create(MyPageViewLikeService::class.java)
    val myPageViewNewService = retrofit.create(MyPageViewNewService::class.java)
    val myPageViewMoreService = retrofit.create(MyPageViewMoreService::class.java)
    val myPageViewFollowService = retrofit.create(
        MyPageViewFollowService::class.java
    )
    val writeViewService: WriteViewService = retrofit.create(WriteViewService::class.java)
    val alarmViewService: AlarmViewService = retrofit.create(AlarmViewService::class.java)
}