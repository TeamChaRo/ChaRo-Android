package com.example.charo_android.presentation.di

import com.example.charo_android.domain.usecase.*
import com.example.charo_android.domain.usecase.home.*
import com.example.charo_android.domain.usecase.search.GetRemoteSearchUseCase
import com.example.charo_android.domain.usecase.signin.GetRemoteSocialLoginData
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpEmailCertificationUseCase
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpEmailCheckUseCase
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpNickNameCheckUseCase
import com.example.charo_android.domain.usecase.signup.PostRemoteSignUpRegisterUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{ GetRemoteBannerUseCase(get()) }
    single{ GetRemoteCustomThemeUseCase(get()) }
    single{ GetRemoteLocalDriveUseCase(get()) }
    single{ GetRemoteTodayCharoDriveUseCase(get()) }
    single{ GetRemoteTrendDriveUseCase(get()) }
    single{ GetRemoteSignUpEmailCheckUseCase(get()) }
    single{GetRemoteMoreDriveUseCase(get())}
    single{GetRemoteMoreLastIdUseCase(get())}
    single{GetRemoteHomeTitle(get())}
    single{GetRemoteMoreNewDriveUseCase(get())}
    single{ GetRemoteSignUpEmailCertificationUseCase(get()) }
    single{ GetRemoteSignUpNickNameCheckUseCase(get())}
    single{ PostRemoteSignUpRegisterUseCase(get())}
    single{ GetRemoteSearchUseCase(get())}
    single{ GetRemoteSocialLoginData(get())}

}