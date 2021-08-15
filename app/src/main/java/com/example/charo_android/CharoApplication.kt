package com.example.charo_android

import android.app.Application
import com.example.charo_android.di.dataSourceModule
import com.example.charo_android.di.networkModule
import com.example.charo_android.di.repositoryModule
import com.example.charo_android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(networkModule)
            modules(viewModelModule)
            modules(dataSourceModule)
            modules(repositoryModule)
        }
    }
}