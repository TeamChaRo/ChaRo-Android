package com.example.charo_android.api

import com.example.charo_android.api.login.SignInViewService
import com.example.charo_android.api.mypage.MyPageViewNewService
import com.example.charo_android.api.mypage.MyPageViewLikeService
import com.example.charo_android.api.mypage.MyPageViewMoreService
import com.example.charo_android.hidden.Hidden
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = Hidden.baseUrl
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moreViewService: MoreViewService = retrofit.create(MoreViewService::class.java)
    val detailViewService: DetailViewService = retrofit.create(DetailViewService::class.java)
    val mainViewService:  HomeViewService = retrofit.create(HomeViewService::class.java)
    val signInViewService: SignInViewService = retrofit.create(SignInViewService::class.java)
    val detailViewLikeService: DetailViewLikeService = retrofit.create(DetailViewLikeService::class.java)
    val detailViewSaveService: DetailViewSaveService = retrofit.create(DetailViewSaveService::class.java)
    val moreViewNewService: MoreViewNewService = retrofit.create(MoreViewNewService::class.java)
    val searchViewService : SearchViewService = retrofit.create(SearchViewService::class.java)
    val myPageViewLikeService = retrofit.create(MyPageViewLikeService::class.java)
    val myPageViewNewService = retrofit.create(MyPageViewNewService::class.java)
    val myPageViewMoreService = retrofit.create(MyPageViewMoreService::class.java)
    val writeViewService: WriteViewService = retrofit.create(WriteViewService::class.java)
}