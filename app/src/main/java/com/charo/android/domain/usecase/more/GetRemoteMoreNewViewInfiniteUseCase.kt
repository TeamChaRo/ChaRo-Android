package com.charo.android.domain.usecase.more

import com.charo.android.domain.model.more.MoreView
import com.charo.android.domain.repository.moreview.MoreViewInfiniteRepository

class GetRemoteMoreNewViewInfiniteUseCase(private val repository : MoreViewInfiniteRepository) {

    suspend fun execute(userId:String, identifer: String, postId : Int, value : String) : MoreView {

        return repository.getNewPreview(userId, identifer, postId, value)

    }
}