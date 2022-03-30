package com.charo.android.domain.repository.moreview

import com.example.charo_android.domain.model.more.MoreView

interface MoreViewInfiniteRepository {
    suspend fun getPreview(userId:String, identifer: String, postId : Int, count: Int, value : String)
            : MoreView


    suspend fun getNewPreview(userId:String, identifer: String, postId : Int, value : String)
            : MoreView
}