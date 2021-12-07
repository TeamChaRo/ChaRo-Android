package com.example.charo_android.presentation.di

import android.app.Application
import com.example.charo_android.hidden.Hidden
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, Hidden.kakao)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CharoApplication)
            modules(viewModelModule)
            modules(networkModule)
            modules(dataSourceModule)
            modules(repositoryModule)
            modules(useCaseModule)

        }
    }
}