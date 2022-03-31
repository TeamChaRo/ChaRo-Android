package com.charo.android.data.api.search


import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.data.model.response.search.ResponseSearchViewData
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchViewService {
    @POST("/post/search/like")
    suspend fun postSearch(
        @Body body: RequestSearchViewData
    ) : ResponseSearchViewData
}