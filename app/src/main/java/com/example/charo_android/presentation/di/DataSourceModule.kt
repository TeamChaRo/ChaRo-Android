package com.example.charo_android.presentation.di

import com.example.charo_android.data.datasource.local.home.LocalHomeThemeDataSource
import com.example.charo_android.data.datasource.local.home.LocalHomeThemeDataSourceImpl
import com.example.charo_android.data.datasource.remote.detailpost.RemoteDetailPostDataSource
import com.example.charo_android.data.datasource.remote.detailpost.RemoteDetailPostDataSourceImpl
import com.example.charo_android.data.datasource.remote.follow.RemoteFollowDataSource
import com.example.charo_android.data.datasource.remote.follow.RemoteFollowDataSourceImpl
import com.example.charo_android.data.datasource.remote.home.RemoteHomeDataSource
import com.example.charo_android.data.datasource.remote.home.RemoteHomeDataSourceImpl
import com.example.charo_android.data.datasource.remote.more.*
import com.example.charo_android.data.datasource.remote.mypage.RemoteMyPageDataSource
import com.example.charo_android.data.datasource.remote.mypage.RemoteMyPageDataSourceImpl
import com.example.charo_android.data.datasource.remote.search.RemoteSearchViewDataSource
import com.example.charo_android.data.datasource.remote.search.RemoteSearchViewDataSourceImpl
import com.example.charo_android.data.datasource.remote.setting.RemoteSettingDataSource
import com.example.charo_android.data.datasource.remote.setting.RemoteSettingDataSourceImpl
import com.example.charo_android.data.datasource.remote.signin.RemoteEmailSignInDataSource
import com.example.charo_android.data.datasource.remote.signin.RemoteEmailSignInDataSourceImpl
import com.example.charo_android.data.datasource.remote.signin.RemoteKakaoSignInDataSource
import com.example.charo_android.data.datasource.remote.signin.RemoteKakaoSignInDataSourceImpl
import com.example.charo_android.data.datasource.remote.signup.*
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteHomeDataSource> { RemoteHomeDataSourceImpl(get()) }
    single<RemoteSignUpEmailCheckDataSource> { RemoteSignUpEmailCheckDataSourceImpl(get()) }
    single<RemoteMoreViewInfiniteDataSource> { RemoteMoreViewInfiniteDataSourceImpl(get()) }
    single<RemoteMoreViewDataSource> { RemoteMoreViewDataSourceImpl(get()) }
    single<RemoteMoreNewViewDataSource> { RemoteMoreNewViewDataSourceImpl(get()) }
    single<RemoteSignUpEmailCertificationDataSource> {
        RemoteSignUpEmailCertificationDataSourceImpl(
            get()
        )
    }
    single<RemoteSignUpNickNameCheckDataSource> { RemoteSignUpNickNameCheckDataSourceImpl(get()) }
    single<RemoteSignUpRegisterDataSource> { RemoteSignUpRegisterDataSourceImpl(get()) }
    single<LocalHomeThemeDataSource> { LocalHomeThemeDataSourceImpl() }
    single<RemoteSearchViewDataSource> { RemoteSearchViewDataSourceImpl(get()) }
    single<RemoteKakaoSignInDataSource> { RemoteKakaoSignInDataSourceImpl(get()) }
    single<RemoteEmailSignInDataSource> { RemoteEmailSignInDataSourceImpl(get()) }
    single<RemoteSettingDataSource> { RemoteSettingDataSourceImpl(get()) }
    // SH
    single<RemoteMyPageDataSource> { RemoteMyPageDataSourceImpl(get()) }
    single<RemoteFollowDataSource> { RemoteFollowDataSourceImpl(get()) }
    single<RemoteDetailPostDataSource> { RemoteDetailPostDataSourceImpl(get()) }
}