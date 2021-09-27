package com.example.charo_android.presentation.di

import com.example.charo_android.data.api.HomeViewService
import com.example.charo_android.data.api.more.MoreViewInfiniteService

import com.example.charo_android.hidden.Hidden
import com.example.charo_android.data.api.SignUpEmailCheckViewService
import com.example.charo_android.data.api.more.MoreNewViewService
import com.example.charo_android.data.api.more.MoreViewService
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

    single<SignUpEmailCheckViewService>{
        get<Retrofit>().create(SignUpEmailCheckViewService::class.java)
    }

    single<MoreViewInfiniteService>{
        get<Retrofit>().create(MoreViewInfiniteService::class.java)
    }

    single<MoreViewService>{
        get<Retrofit>().create(MoreViewService::class.java)
    }

    single<MoreNewViewService>{
        get<Retrofit>().create(MoreNewViewService::class.java)
    }
}
