package com.example.charo_android.presentation.di

import android.app.Application
import com.example.charo_android.presentation.di.networkModule
import com.example.charo_android.presentation.di.dataSourceModule
import com.example.charo_android.presentation.di.repositoryModule
import com.example.charo_android.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CharoApplication)
            modules(viewModelModule)
            modules(networkModule)
            modules(dataSourceModule)
            modules(repositoryModule)
            modules(useCaseModule)
        }
    }
}