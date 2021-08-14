package com.example.charo_android

import android.app.Application
import com.example.charo_android.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                networkModule
            )
        }
    }
}