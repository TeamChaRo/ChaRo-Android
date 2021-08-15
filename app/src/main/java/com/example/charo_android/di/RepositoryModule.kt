package com.example.charo_android.di

import com.example.charo_android.data.repository.HomeRepository
import com.example.charo_android.data.repository.HomeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get())}
}