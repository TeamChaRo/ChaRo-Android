package com.example.charo_android.presentation.di

import com.example.charo_android.domain.usecase.*
import com.example.charo_android.domain.usecase.home.*
import com.example.charo_android.domain.usecase.search.GetRemoteSearchUseCase
import com.example.charo_android.domain.usecase.setting.*
import com.example.charo_android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import com.example.charo_android.domain.usecase.signin.GetRemoteSocialLoginData
import com.example.charo_android.domain.usecase.signup.*
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
    single{ GetRemoteEmailLoginUseCase(get())}
    single{ ProfileImageChangeUseCase(get())}
    single{ ProfileNickNameChangeUseCase(get())}
    single { WithdrawalUserUseCase(get())}
    single { ProfilePasswordCheckUseCase(get()) }
    single { NewPasswordRegisterUseCase(get()) }
    single{PostRemoteHomeLikeUseCase(get())}
    single{ PostRemoteSocialSignUpRegisterUseCase(get()) }
    single{PostRemoteKaKaoSignUpRegisterUseCase(get())}
}