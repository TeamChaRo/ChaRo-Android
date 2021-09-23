package com.example.charo_android.presentation.di

import com.example.charo_android.domain.repository.HomeRepository
import com.example.charo_android.data.repository.HomeRepositoryImpl
import com.example.charo_android.data.repository.SignUpRepositoryImpl
import com.example.charo_android.domain.repository.SignUpRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get())}
    single<SignUpRepository> { SignUpRepositoryImpl(get())}
}