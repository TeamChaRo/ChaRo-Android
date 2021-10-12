package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSource
import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSourceImpl
import com.example.charo_android.data.repository.remote.more.*
import com.example.charo_android.data.repository.remote.signup.*
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteHomeDataSource>{ RemoteHomeDataSourceImpl(get()) }
    single<RemoteSignUpEmailCheckDataSource> { RemoteSignUpEmailCheckDataSourceImpl(get())}
    single<RemoteMoreViewInfiniteDataSource> { RemoteMoreViewInfiniteDataSourceImpl(get())}
    single<RemoteMoreViewDataSource> { RemoteMoreViewDataSourceImpl(get())}
    single<RemoteMoreNewViewDataSource>{RemoteMoreNewViewDataSourceImpl(get())}
    single<RemoteSignUpEmailCertificationDataSource>{RemoteSignUpEmailCertificationDataSourceImpl(get())}
    single<RemoteSignUpNickNameCheckDataSource>{RemoteSignUpNickNameCheckDataSourceImpl(get())}
    single<RemoteSignUpRegisterDataSource>{RemoteSignUpRegisterDataSourceImpl(get())}
}