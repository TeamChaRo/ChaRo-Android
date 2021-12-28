package com.example.charo_android.domain.usecase.more

import com.example.charo_android.domain.model.more.MoreView
import com.example.charo_android.domain.repository.moreview.MoreViewInfiniteRepository

class GetRemoteMoreViewInfiniteUseCase(private val repository : MoreViewInfiniteRepository) {

    suspend fun execute(userId:String, identifer: String, postId : Int, count: Int, value : String) : MoreView{
        return repository.getPreview(userId, identifer, postId, count, value)

    }
}