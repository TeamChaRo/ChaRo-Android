package com.example.charo_android.data.model.mypage

data class ResponseLikePost(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val userInformation: UserInformation,
        val writtenPost: WrittenPost,
        val savedPost: SavedPost
    )
}
