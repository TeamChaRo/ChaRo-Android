package com.example.charo_android.data.api

import com.example.charo_android.data.api.detail.DetailViewService
import com.example.charo_android.data.api.login.SignInViewService
import com.example.charo_android.data.api.more.MoreViewInfiniteService
import com.example.charo_android.data.api.mypage.MyPageViewLikeService
import com.example.charo_android.data.api.mypage.MyPageViewMoreService
import com.example.charo_android.data.api.mypage.MyPageViewNewService
import com.example.charo_android.data.api.search.SearchViewService
import com.example.charo_android.data.api.write.WriteViewService
import com.example.charo_android.hidden.Hidden

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = Hidden.baseUrl
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moreViewService: MoreViewInfiniteService = retrofit.create(MoreViewInfiniteService::class.java)
    val detailViewService: DetailViewService = retrofit.create(DetailViewService::class.java)
    val signInViewService: SignInViewService = retrofit.create(SignInViewService::class.java)
    val searchViewService : SearchViewService = retrofit.create(SearchViewService::class.java)
    val myPageViewLikeService = retrofit.create(MyPageViewLikeService::class.java)
    val myPageViewNewService = retrofit.create(MyPageViewNewService::class.java)
    val myPageViewMoreService = retrofit.create(MyPageViewMoreService::class.java)
    val writeViewService: WriteViewService = retrofit.create(WriteViewService::class.java)
}