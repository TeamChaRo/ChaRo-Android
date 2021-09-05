package com.example.charo_android.data.mypage

data class ResponseMyPageSortedByPopularData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data (
        val savedPost: SavedPost,
        val userInformation: UserInformation,
        val writtenPost: WrittenPost
    )
}