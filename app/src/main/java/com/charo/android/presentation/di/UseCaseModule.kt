package com.charo.android.presentation.di


import com.charo.android.domain.usecase.detailpost.GetDetailPostLikeUserListUseCase
import com.charo.android.domain.usecase.detailpost.GetDetailPostUseCase
import com.charo.android.domain.usecase.follow.GetRemoteFollowListUseCase
import com.charo.android.domain.usecase.follow.PostFollowUseCase
import com.charo.android.domain.usecase.home.*
import com.charo.android.domain.usecase.interaction.PostLikeUseCase
import com.charo.android.domain.usecase.interaction.PostSaveUseCase
import com.charo.android.domain.usecase.more.*
import com.charo.android.domain.usecase.mypage.*
import com.charo.android.domain.usecase.search.GetRemoteSearchUseCase
import com.charo.android.domain.usecase.setting.*
import com.charo.android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import com.charo.android.domain.usecase.signin.GetRemoteSocialLoginData
import com.charo.android.domain.usecase.signup.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRemoteBannerUseCase(get()) }
    single { GetRemoteCustomThemeUseCase(get()) }
    single { GetRemoteLocalDriveUseCase(get()) }
    single { GetRemoteTodayCharoDriveUseCase(get()) }
    single { GetRemoteTrendDriveUseCase(get()) }
    single { GetRemoteSignUpEmailCheckUseCase(get()) }
    single { GetRemoteMoreDriveUseCase(get()) }
    single { GetRemoteMoreLastIdUseCase(get()) }
    single { GetRemoteHomeTitle(get()) }
    single { GetRemoteMoreNewDriveUseCase(get()) }
    single { GetRemoteSignUpEmailCertificationUseCase(get()) }
    single { GetRemoteSignUpNickNameCheckUseCase(get()) }
    single { PostRemoteSignUpRegisterUseCase(get()) }
    single { GetRemoteSearchUseCase(get()) }
    single { GetRemoteSocialLoginData(get()) }
    single { GetRemoteEmailLoginUseCase(get()) }
    single { ProfileImageChangeUseCase(get()) }
    single { ProfileNickNameChangeUseCase(get()) }
    single { WithdrawalUserUseCase(get()) }
    single { ProfilePasswordCheckUseCase(get()) }
    single { NewPasswordRegisterUseCase(get()) }


    single { PostRemoteHomeLikeUseCase(get()) }
    single { PostRemoteSocialSignUpRegisterUseCase(get()) }
    single { PostRemoteKaKaoSignUpRegisterUseCase(get()) }
    single { GetRemoteMoreViewInfiniteUseCase(get()) }
    // SH
    single { GetRemoteLikePostUseCase(get()) }
    single { GetRemoteNewPostUseCase(get()) }
    single { GetRemoteMoreWrittenLikePostUseCase(get()) }
    single { GetRemoteMoreWrittenNewPostUseCase(get()) }
    single { GetRemoteMoreSavedLikePostUseCase(get()) }
    single { GetRemoteMoreSavedNewPostUseCase(get()) }
    single { GetRemoteFollowListUseCase(get()) }
    single { GetDetailPostUseCase(get()) }
    single { PostLikeUseCase(get()) }
    single { PostSaveUseCase(get()) }
    single { GetDetailPostLikeUserListUseCase(get()) }
    single { PostFollowUseCase(get()) }

    single { GetRemoteMoreNewViewInfiniteUseCase(get()) }
    single { GetRemoteMoreNewLastIdUseCase(get()) }

}