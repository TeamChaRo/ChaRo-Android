package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.detailpost.ResponseDetailPost
import com.example.charo_android.domain.model.detailpost.DetailPost

object DetailPostMapper {
    fun mapperToDetailPost(responseDetailPost: ResponseDetailPost): DetailPost {
        val data = responseDetailPost.data
        return DetailPost(
            images = data.images,
            province = data.province,
            isParking = data.isParking,
            parkingDesc = data.parkingDesc,
            courseDesc = data.courseDesc,
            themes = data.themes,
            warnings = data.warnings,
            author = data.author,
            authorEmail = data.authorEmail,
            isAuthor = data.isAuthor,
            profileImage = data.profileImage,
            likesCount = data.likesCount,
            isFavorite = data.isFavorite,
            isStored = data.isStored,
            course = data.course.map {
                DetailPost.Course(
                    address = it.address,
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        )
    }
}