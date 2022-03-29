package com.charo.android.domain.usecase.more

import com.example.charo_android.domain.model.more.MoreView
import com.example.charo_android.domain.repository.moreview.MoreViewInfiniteRepository

class GetRemoteMoreNewViewInfiniteUseCase(private val repository : MoreViewInfiniteRepository) {

    suspend fun execute(userId:String, identifer: String, postId : Int, value : String) : MoreView{

        return repository.getNewPreview(userId, identifer, postId, value)

    }
}