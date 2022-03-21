package com.example.charo_android.presentation.di

import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.example.charo_android.presentation.ui.follow.viewmodel.FollowViewModel
import com.example.charo_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.example.charo_android.presentation.ui.mypage.viewmodel.OtherMyPageViewModel
import com.example.charo_android.presentation.ui.search.viewmodel.SearchViewModel
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.ui.signin.viewmodel.EmailSignInViewModel
import com.example.charo_android.presentation.ui.signin.viewmodel.SocialSignInViewModel
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SignUpEmailViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { MoreViewViewModel(get(), get(), get(), get(), get(),get(),get()) }
    viewModel { SharedViewModel(get()) }
    viewModel { SettingViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SocialSignInViewModel(get()) }
    viewModel { EmailSignInViewModel(get()) }
    // SH
    viewModel { MyPageViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { FollowViewModel(get(), get()) }
    viewModel { DetailPostViewModel(get(), get(), get(), get(), get()) }
    viewModel { OtherMyPageViewModel(get(), get(), get(), get(), get()) }
}