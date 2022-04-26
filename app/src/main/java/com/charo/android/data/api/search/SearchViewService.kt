package com.charo.android.data.api.search


import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchViewService {
    //검색 좋아요
    @POST("/post/search/like")
    suspend fun postSearchLike(
        @Body body: RequestSearchViewData
    ) : ResponseSearchViewData

    //검색 최신순
    @POST("/post/search/new")
    suspend fun postSearchNew(
        @Body body: RequestSearchViewData
    ) : ResponseSearchViewData
}