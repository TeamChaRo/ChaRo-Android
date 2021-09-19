package com.example.charo_android.data.api
import com.example.charo_android.data.api.login.SignInViewService
import com.example.charo_android.data.api.mypage.MyPageViewSortedByDateService
import com.example.charo_android.data.api.mypage.MyPageViewSortedByPopularService
import com.example.charo_android.data.api.hidden.Hidden
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = Hidden.baseUrl
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val homeViewService: HomeViewService = retrofit.create(HomeViewService::class.java)
    val moreViewService: MoreViewService = retrofit.create(MoreViewService::class.java)
    val detailViewService: DetailViewService = retrofit.create(DetailViewService::class.java)
    val signInViewService: SignInViewService = retrofit.create(SignInViewService::class.java)
    val detailViewLikeService: DetailViewLikeService = retrofit.create(DetailViewLikeService::class.java)
    val detailViewSaveService: DetailViewSaveService = retrofit.create(DetailViewSaveService::class.java)
    val moreViewNewService: MoreViewNewService = retrofit.create(MoreViewNewService::class.java)
    val searchViewService : SearchViewService = retrofit.create(SearchViewService::class.java)
    val myPageViewSortedByPopularService: MyPageViewSortedByPopularService = retrofit.create(
        MyPageViewSortedByPopularService::class.java)
    val myPageViewSortedByDateService: MyPageViewSortedByDateService = retrofit.create(
        MyPageViewSortedByDateService::class.java)
    val writeViewService: WriteViewService = retrofit.create(WriteViewService::class.java)
}