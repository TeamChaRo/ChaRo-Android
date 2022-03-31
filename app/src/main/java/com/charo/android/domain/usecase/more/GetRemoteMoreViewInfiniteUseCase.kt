package com.charo.android.domain.usecase.more

import com.charo.android.domain.model.more.MoreView
import com.charo.android.domain.repository.moreview.MoreViewInfiniteRepository

class GetRemoteMoreViewInfiniteUseCase(private val repository : MoreViewInfiniteRepository) {

    suspend fun execute(userId:String, identifer: String, postId : Int, count: Int, value : String) : MoreView {
        return repository.getPreview(userId, identifer, postId, count, value)

    }
}