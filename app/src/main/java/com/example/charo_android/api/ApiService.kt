package com.example.charo_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "http://3.139.62.132:5000"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moreViewService: MoreViewService = retrofit.create(MoreViewService::class.java)
    val detailViewService: DetailViewService = retrofit.create(DetailViewService::class.java)
    val mainViewService:  HomeViewService = retrofit.create(HomeViewService::class.java)
    val moreviewNewService: MoreViewNewService = retrofit.create(MoreViewNewService::class.java)
    val searchViewService : SearchViewService = retrofit.create(SearchViewService::class.java)
}

