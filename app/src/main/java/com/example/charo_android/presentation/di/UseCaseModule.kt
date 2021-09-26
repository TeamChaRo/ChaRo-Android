package com.example.charo_android.presentation.di

import com.example.charo_android.domain.usecase.*
import com.example.charo_android.domain.usecase.home.*
import org.koin.dsl.module

val useCaseModule = module{
    single{ GetRemoteBannerUseCase(get()) }
    single{ GetRemoteCustomThemeUseCase(get()) }
    single{ GetRemoteLocalDriveUseCase(get()) }
    single{ GetRemoteTodayCharoDriveUseCase(get()) }
    single{ GetRemoteTrendDriveUseCase(get()) }
    single{GetRemoteSignUpEmailCheckUseCase(get())}
    single{GetRemoteMoreDriveUseCase(get())}
    single{GetRemoteMoreLastIdUseCase(get())}
    single{GetRemoteHomeTitle(get())}
    single{GetRemoteMoreNewDriveUseCase(get())}

}