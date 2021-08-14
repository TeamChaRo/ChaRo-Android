package com.example.charo_android.data.response

import com.example.charo_android.data.SavedPost
import com.example.charo_android.data.UserInformation
import com.example.charo_android.data.WrittenPost

data class ResponseMyPageLikeData(
    val data: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val savedPost: List<SavedPost>,
        val savedTotal: Int,
        val userInformation: UserInformation,
        val writtenPost: List<WrittenPost>,
        val writtenTotal: Int
    )
}