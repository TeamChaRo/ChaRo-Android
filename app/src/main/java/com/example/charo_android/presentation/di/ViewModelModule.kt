package com.example.charo_android.presentation.di

import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.example.charo_android.presentation.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{HomeViewModel( get(),get(),get(),get(),get())}
    viewModel{SignUpViewModel(get())}
    viewModel{MoreViewViewModel(get(),get(),get())}
    viewModel{SharedViewModel(get())}

}