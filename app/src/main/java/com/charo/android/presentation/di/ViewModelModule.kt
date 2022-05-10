package com.charo.android.presentation.di


import com.charo.android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.charo.android.presentation.ui.follow.viewmodel.FollowViewModel
import com.charo.android.presentation.ui.home.viewmodel.HomeViewModel
import com.charo.android.presentation.ui.main.SharedViewModel
import com.charo.android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.charo.android.presentation.ui.mypage.viewmodel.OtherMyPageViewModel
import com.charo.android.presentation.ui.search.viewmodel.SearchViewModel
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.ui.signin.viewmodel.EmailSignInViewModel
import com.charo.android.presentation.ui.signin.viewmodel.PasswordSearchViewModel
import com.charo.android.presentation.ui.signin.viewmodel.SocialSignInViewModel
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.charo.android.presentation.ui.write.WriteSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SignUpEmailViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { MoreViewViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SharedViewModel(get()) }
    viewModel { SettingViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SocialSignInViewModel(get()) }
    viewModel { EmailSignInViewModel(get()) }
    viewModel { PasswordSearchViewModel(get()) }
    // SH
    viewModel { MyPageViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { FollowViewModel(get(), get()) }
    viewModel { DetailPostViewModel(get(), get(), get(), get(), get()) }
    viewModel { OtherMyPageViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { WriteSharedViewModel(get(), get(), get(), get(), get(), get()) }
}