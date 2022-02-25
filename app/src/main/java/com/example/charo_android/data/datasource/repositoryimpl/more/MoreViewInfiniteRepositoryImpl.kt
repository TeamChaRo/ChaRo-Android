package com.example.charo_android.data.datasource.repositoryimpl.more

import com.example.charo_android.data.mapper.MoreViewMapper
import com.example.charo_android.data.datasource.remote.more.RemoteMoreViewInfiniteDataSource
import com.example.charo_android.domain.model.more.MoreView
import com.example.charo_android.domain.repository.moreview.MoreViewInfiniteRepository

class MoreViewInfiniteRepositoryImpl(private val dataSource: RemoteMoreViewInfiniteDataSource) :
    MoreViewInfiniteRepository {
    override suspend fun getPreview(
        userId: String,
        identifer: String,
        postId: Int,
        count: Int,
        value: String
    ): MoreView {
        return MoreViewMapper.mapperToInfiniteMoreView(
            dataSource.getPreview(
                userId,
                identifer,
                postId,
                count,
                value
            )
        )
    }
}