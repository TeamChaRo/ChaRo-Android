package com.charo.android.presentation.di

import android.app.Application
import com.charo.android.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics

import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class CharoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CharoApplication)
            modules(viewModelModule)
            modules(networkModule)
            modules(dataSourceModule)
            modules(repositoryModule)
            modules(useCaseModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
    }
}