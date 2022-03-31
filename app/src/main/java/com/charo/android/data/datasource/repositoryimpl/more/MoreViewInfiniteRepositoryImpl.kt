package com.charo.android.data.datasource.repositoryimpl.more

import com.charo.android.data.datasource.remote.more.RemoteMoreViewInfiniteDataSource
import com.charo.android.data.mapper.MoreViewMapper
import com.charo.android.domain.model.more.MoreView
import com.charo.android.domain.repository.moreview.MoreViewInfiniteRepository


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


    override suspend fun getNewPreview(
        userId: String,
        identifer: String,
        postId: Int,
        value: String
    ): MoreView {
        return MoreViewMapper.mapperToInfiniteMoreView(
            dataSource.getNewPreview(
                userId, identifer, postId, value
            )
        )
    }
}