package com.example.charo_android.di

import com.example.charo_android.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel{HomeViewModel()}
}