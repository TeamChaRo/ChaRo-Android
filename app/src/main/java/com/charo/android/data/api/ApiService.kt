package com.charo.android.data.api

import com.example.charo_android.data.api.alarm.AlarmViewService
import com.example.charo_android.data.api.detail.DetailViewService
import com.example.charo_android.data.api.charo.MyPageViewFollowService
import com.example.charo_android.data.api.charo.MyPageViewLikeService
import com.example.charo_android.data.api.charo.MyPageViewMoreService
import com.example.charo_android.data.api.charo.MyPageViewNewService
import com.example.charo_android.data.api.mypage.MyPageService
import com.example.charo_android.data.api.write.WriteViewService
import com.example.charo_android.hidden.Hidden
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = Hidden.baseUrl

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(com.charo.android.data.api.ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val detailViewService: DetailViewService = com.charo.android.data.api.ApiService.retrofit.create(DetailViewService::class.java)
    val myPageViewLikeService = com.charo.android.data.api.ApiService.retrofit.create(MyPageViewLikeService::class.java)
    val myPageViewNewService = com.charo.android.data.api.ApiService.retrofit.create(MyPageViewNewService::class.java)
    val myPageViewMoreService = com.charo.android.data.api.ApiService.retrofit.create(MyPageViewMoreService::class.java)
    val myPageViewFollowService = com.charo.android.data.api.ApiService.retrofit.create(MyPageViewFollowService::class.java)
    val writeViewService: WriteViewService = com.charo.android.data.api.ApiService.retrofit.create(WriteViewService::class.java)
    val alarmViewService: AlarmViewService = com.charo.android.data.api.ApiService.retrofit.create(AlarmViewService::class.java)
}