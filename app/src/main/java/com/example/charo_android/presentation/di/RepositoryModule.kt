package com.example.charo_android.presentation.di

import com.example.charo_android.data.repository.*
import com.example.charo_android.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get())}
    single<SignUpRepository> { SignUpRepositoryImpl(get())}
    single<MoreViewInfiniteRepository>{MoreViewInfiniteRepositoryImpl(get())}
    single<MoreViewRepository>{MoreViewRepositoryImpl(get())}
    single<MoreNewViewRepository>{MoreNewViewRepositoryImpl(get())}
}