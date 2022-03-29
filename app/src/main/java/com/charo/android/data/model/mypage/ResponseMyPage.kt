package com.charo.android.data.model.mypage

data class ResponseMyPage(
    val success: Boolean,
    val msg: String,
    val data: Data
) {
    data class Data(
        val userInformation: UserInformation,
        val writtenPost: PostInfo,
        val savedPost: PostInfo
    )
}
