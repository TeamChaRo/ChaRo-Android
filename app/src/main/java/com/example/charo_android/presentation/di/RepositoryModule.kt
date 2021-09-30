package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.*
import com.example.charo_android.data.repository.signup.SignUpEmailCertificationRepositoryImpl
import com.example.charo_android.data.repository.signup.SignUpNickNameCheckRepositoryImpl
import com.example.charo_android.data.repository.signup.SignUpRepositoryImpl
import com.example.charo_android.domain.repository.*
import com.example.charo_android.domain.repository.signup.SignUpEmailCertificationRepository
import com.example.charo_android.domain.repository.signup.SignUpNickNameCheckRepository
import com.example.charo_android.domain.repository.signup.SignUpRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get())}
    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
    single<MoreViewInfiniteRepository>{MoreViewInfiniteRepositoryImpl(get())}
    single<MoreViewRepository>{MoreViewRepositoryImpl(get())}
    single<MoreNewViewRepository>{MoreNewViewRepositoryImpl(get())}
    single<SignUpEmailCertificationRepository>{SignUpEmailCertificationRepositoryImpl(get())}
    single<SignUpNickNameCheckRepository>{SignUpNickNameCheckRepositoryImpl(get())}
}