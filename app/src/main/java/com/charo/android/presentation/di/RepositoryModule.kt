package com.charo.android.presentation.di

import com.charo.android.data.datasource.repositoryimpl.detailpost.DetailPostRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.follow.FollowRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.home.HomeRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.interaction.InteractionRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.more.MoreNewViewRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.more.MoreViewInfiniteRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.more.MoreViewRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.mypage.MyPageRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.search.SearchViewRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.setting.SettingRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signin.EmailSignInRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signin.KakaoSignInRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signup.SignUpEmailCertificationRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signup.SignUpNickNameCheckRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signup.SignUpRegisterRepositoryImpl
import com.charo.android.data.datasource.repositoryimpl.signup.SignUpRepositoryImpl
import com.charo.android.domain.repository.detailpost.DetailPostRepository
import com.charo.android.domain.repository.follow.FollowRepository
import com.charo.android.domain.repository.home.HomeRepository
import com.charo.android.domain.repository.interaction.InteractionRepository
import com.charo.android.domain.repository.moreview.MoreNewViewRepository
import com.charo.android.domain.repository.moreview.MoreViewInfiniteRepository
import com.charo.android.domain.repository.moreview.MoreViewRepository
import com.charo.android.domain.repository.mypage.MyPageRepository
import com.charo.android.domain.repository.search.SearchViewRepository
import com.charo.android.domain.repository.setting.SettingRepository
import com.charo.android.domain.repository.signin.EmailSignInRepository
import com.charo.android.domain.repository.signin.KakaoSignInRepository
import com.charo.android.domain.repository.signup.SignUpEmailCertificationRepository
import com.charo.android.domain.repository.signup.SignUpNickNameCheckRepository
import com.charo.android.domain.repository.signup.SignUpRegisterRepository
import com.charo.android.domain.repository.signup.SignUpRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
    single<MoreViewInfiniteRepository> { MoreViewInfiniteRepositoryImpl(get()) }
    single<MoreViewRepository> { MoreViewRepositoryImpl(get()) }
    single<MoreNewViewRepository> { MoreNewViewRepositoryImpl(get()) }
    single<SignUpEmailCertificationRepository> { SignUpEmailCertificationRepositoryImpl(get()) }
    single<SignUpNickNameCheckRepository> { SignUpNickNameCheckRepositoryImpl(get()) }
    single<SignUpRegisterRepository> { SignUpRegisterRepositoryImpl(get()) }
    single<SearchViewRepository> { SearchViewRepositoryImpl(get()) }
    single<KakaoSignInRepository> { KakaoSignInRepositoryImpl(get()) }
    single<EmailSignInRepository> { EmailSignInRepositoryImpl(get()) }
    single<SettingRepository> { SettingRepositoryImpl(get()) }
    // SH
    single<MyPageRepository> { MyPageRepositoryImpl(get()) }
    single<FollowRepository> { FollowRepositoryImpl(get()) }
    single<DetailPostRepository> { DetailPostRepositoryImpl(get()) }
    single<InteractionRepository> { InteractionRepositoryImpl(get()) }
}