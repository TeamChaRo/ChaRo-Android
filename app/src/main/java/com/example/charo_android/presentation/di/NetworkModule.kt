package com.example.charo_android.presentation.di

import com.example.charo_android.data.api.HomeViewService
import com.example.charo_android.data.api.more.MoreViewInfiniteService

import com.example.charo_android.hidden.Hidden
import com.example.charo_android.data.api.signup.SignUpEmailCheckViewService
import com.example.charo_android.data.api.more.MoreNewViewService
import com.example.charo_android.data.api.more.MoreViewService
import com.example.charo_android.data.api.signup.SignUpEmailCertificationViewService
import com.example.charo_android.data.api.signup.SignUpNickNameCheckViewService
import com.example.charo_android.data.api.signup.SignUpRegisterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val interceptor = HttpLoggingInterceptor()
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    client.addInterceptor(interceptor)
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Hidden.baseUrl)
            .client(client.build())
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

    single<SignUpEmailCertificationViewService>{
        get<Retrofit>().create(SignUpEmailCertificationViewService::class.java)
    }

    single<SignUpNickNameCheckViewService>{
        get<Retrofit>().create(SignUpNickNameCheckViewService::class.java)
    }
    single<SignUpRegisterService>{
        get<Retrofit>().create(SignUpRegisterService::class.java)
    }
}

