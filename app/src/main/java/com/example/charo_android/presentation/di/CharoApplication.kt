package com.example.charo_android.presentation.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "1c4649ba2d391b77eba1164b785bc1e1")
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