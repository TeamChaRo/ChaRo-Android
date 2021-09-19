package com.example.charo_android.presentation.di

import com.example.charo_android.data.api.HomeViewService
import com.example.charo_android.data.api.hidden.Hidden
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Hidden.baseUrl)
            .build()
    }

    single<HomeViewService> {
        get<Retrofit>().create(HomeViewService::class.java)
    }
}

