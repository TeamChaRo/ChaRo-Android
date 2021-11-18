package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.response.search.ResponseSearchViewData
import com.example.charo_android.domain.model.search.SearchDrive

object SearchMapper {
    fun mapperToSearch(responseSearchViewData: ResponseSearchViewData) : List<SearchDrive>{
       return responseSearchViewData.data.drive.map{
           SearchDrive(
               day = it.day,
               image = it.image,
               isFavorite = it.isFavorite,
               month = it.month,
               postId = it.postId,
               region = it.region,
               theme = it.theme,
               title = it.title,
               warning = it.warning,
               year = it.year
           )
       }
    }


}


