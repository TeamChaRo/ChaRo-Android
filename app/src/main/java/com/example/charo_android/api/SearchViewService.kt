package com.example.charo_android.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchViewService {
    @POST("/search/like")
    fun postSearch(
        @Body body: RequestSearchViewData
    ) : Call<ResponseSearchViewData>
}