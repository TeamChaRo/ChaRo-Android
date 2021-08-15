package com.example.charo_android.di

import com.example.charo_android.data.datasource.home.HomeDataSource
import com.example.charo_android.data.datasource.home.RemoteHomeDataSource
import io.reactivex.schedulers.Schedulers.single
import org.koin.dsl.module

val dataSourceModule = module {
    single<HomeDataSource>{RemoteHomeDataSource(get())}
}