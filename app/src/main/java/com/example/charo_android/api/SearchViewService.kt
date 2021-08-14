package com.example.charo_android.api

import com.example.charo_android.data.request.RequestSearchViewData
import com.example.charo_android.data.response.ResponseSearchViewData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchViewService {
    @POST("/search/like")
    fun postSearch(
        @Body body: RequestSearchViewData
    ) : Call<ResponseSearchViewData>
}