package com.example.charo_android.presentation.di

import com.example.charo_android.data.api.follow.FollowService
import com.example.charo_android.data.api.home.HomeViewService
import com.example.charo_android.data.api.signin.KakaoSignInService
import com.example.charo_android.data.api.more.MoreViewInfiniteService

import com.example.charo_android.hidden.Hidden
import com.example.charo_android.data.api.signup.SignUpEmailCheckViewService
import com.example.charo_android.data.api.more.MoreNewViewService
import com.example.charo_android.data.api.more.MoreViewService
import com.example.charo_android.data.api.mypage.MyPageService
import com.example.charo_android.data.api.search.SearchViewService
import com.example.charo_android.data.api.setting.SettingViewService
import com.example.charo_android.data.api.signin.SignInViewService
import com.example.charo_android.data.api.signup.SignUpEmailCertificationViewService
import com.example.charo_android.data.api.signup.SignUpNickNameCheckViewService
import com.example.charo_android.data.api.signup.SignUpRegisterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val interceptor = HttpLoggingInterceptor()
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    client.addInterceptor(interceptor)

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Hidden.baseUrl)
            .client(client.build())
            .build()
    }

    single<HomeViewService> {
        get<Retrofit>().create(HomeViewService::class.java)
    }

    single<SignUpEmailCheckViewService> {
        get<Retrofit>().create(SignUpEmailCheckViewService::class.java)
    }

    single<MoreViewInfiniteService> {
        get<Retrofit>().create(MoreViewInfiniteService::class.java)
    }

    single<MoreViewService> {
        get<Retrofit>().create(MoreViewService::class.java)
    }

    single<MoreNewViewService> {
        get<Retrofit>().create(MoreNewViewService::class.java)
    }

    single<SignUpEmailCertificationViewService> {
        get<Retrofit>().create(SignUpEmailCertificationViewService::class.java)
    }

    single<SignUpNickNameCheckViewService> {
        get<Retrofit>().create(SignUpNickNameCheckViewService::class.java)
    }
    single<SignUpRegisterService> {
        get<Retrofit>().create(SignUpRegisterService::class.java)
    }

    single<SearchViewService> {
        get<Retrofit>().create(SearchViewService::class.java)
    }

    single<KakaoSignInService> {
        get<Retrofit>().create(KakaoSignInService::class.java)
    }
    single<SignInViewService> {
        get<Retrofit>().create(SignInViewService::class.java)
    }
    single<SettingViewService> {
        get<Retrofit>().create(SettingViewService::class.java)
    }
    // SH
    single {
        get<Retrofit>().create(MyPageService::class.java)
    }
    single {
        get<Retrofit>().create(FollowService::class.java)
    }
}

