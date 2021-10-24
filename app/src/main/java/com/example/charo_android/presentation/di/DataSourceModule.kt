package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.local.LocalHomeThemeDataSource
import com.example.charo_android.data.repository.local.LocalHomeThemeDataSourceImpl
import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSource
import com.example.charo_android.data.repository.remote.home.RemoteHomeDataSourceImpl
import com.example.charo_android.data.repository.remote.more.*
import com.example.charo_android.data.repository.remote.search.RemoteSearchViewDataSource
import com.example.charo_android.data.repository.remote.search.RemoteSearchViewDataSourceImpl
import com.example.charo_android.data.repository.remote.signin.RemoteKakaoSignInDataSource
import com.example.charo_android.data.repository.remote.signin.RemoteKakaoSignInDataSourceImpl
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
    single<LocalHomeThemeDataSource>{LocalHomeThemeDataSourceImpl()}
    single<RemoteSearchViewDataSource>{RemoteSearchViewDataSourceImpl(get())}
    single<RemoteKakaoSignInDataSource>{RemoteKakaoSignInDataSourceImpl(get())}
}