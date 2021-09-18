package com.example.charo_android.data.mypage

data class ResponseMyPageSortedByDateData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data (
        val savedPost: Post,
        val userInformation: UserInformation,
        val writtenPost: Post
    )
}