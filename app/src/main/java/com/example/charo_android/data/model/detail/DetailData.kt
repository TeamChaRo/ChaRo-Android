package com.example.charo_android.data.model.detail

data class DetailData(
    var postId: Int,
    var title: String,
    var date: String,
    var region: String,
    var data: ResponseDetailData.Data
)
