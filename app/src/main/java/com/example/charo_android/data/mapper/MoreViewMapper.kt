package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import com.example.charo_android.domain.model.more.*


object MoreViewMapper {

    fun mapperToMoreDrive(responseMoreViewData: ResponseMoreViewData) : List<MoreDrive>{
        return responseMoreViewData.data.drive.map {
            MoreDrive(
                moreDay = it.day,
                moreImage =  it.image,
                moreIsFavorite= it.isFavorite,
                moreMonth= it.month,
                morePostId= it.postId,
                moreRegion= it.region,
                moreTheme= it.theme,
                moreTitle= it.title,
                moreWarning= it.warning,
                moreYear= it.year
            )
        }
    }

    fun mapperToMoreLastId(responseMoreViewData: ResponseMoreViewData) : LastId{
        return LastId(
            lastCount = responseMoreViewData.data.lastCount,
            lastId = responseMoreViewData.data.lastId
        )
    }


}