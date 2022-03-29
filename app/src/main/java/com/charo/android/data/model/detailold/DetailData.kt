package com.charo.android.data.model.detailold

data class DetailData(
    var postId: Int,
    var title: String,
    var date: String,
    var region: String,
    var data: ResponseDetailData.Data
)
