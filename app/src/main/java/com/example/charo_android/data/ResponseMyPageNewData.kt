package com.example.charo_android.data

data class ResponseMyPageNewData(
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