package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.repositoryimpl.follow.FollowRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.home.HomeRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.more.MoreNewViewRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.more.MoreViewInfiniteRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.more.MoreViewRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.mypage.MyPageRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.search.SearchViewRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.setting.SettingRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signin.EmailSignInRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signin.KakaoSignInRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signup.SignUpEmailCertificationRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signup.SignUpNickNameCheckRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signup.SignUpRegisterRepositoryImpl
import com.example.charo_android.data.repository.repositoryimpl.signup.SignUpRepositoryImpl
import com.example.charo_android.domain.repository.follow.FollowRepository
import com.example.charo_android.domain.repository.setting.SettingRepository
import com.example.charo_android.domain.repository.home.HomeRepository
import com.example.charo_android.domain.repository.moreview.MoreNewViewRepository
import com.example.charo_android.domain.repository.moreview.MoreViewInfiniteRepository
import com.example.charo_android.domain.repository.moreview.MoreViewRepository
import com.example.charo_android.domain.repository.mypage.MyPageRepository
import com.example.charo_android.domain.repository.search.SearchViewRepository
import com.example.charo_android.domain.repository.signin.EmailSignInRepository
import com.example.charo_android.domain.repository.signin.KakaoSignInRepository
import com.example.charo_android.domain.repository.signup.SignUpEmailCertificationRepository
import com.example.charo_android.domain.repository.signup.SignUpNickNameCheckRepository
import com.example.charo_android.domain.repository.signup.SignUpRegisterRepository
import com.example.charo_android.domain.repository.signup.SignUpRepository
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
}