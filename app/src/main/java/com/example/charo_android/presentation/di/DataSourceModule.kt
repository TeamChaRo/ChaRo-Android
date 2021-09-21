package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.remote.RemoteHomeDataSource
import com.example.charo_android.data.repository.remote.RemoteHomeDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteHomeDataSource>{ RemoteHomeDataSourceImpl(get()) }
}