package com.charo.android.data.model.request.signup

data class RequestSignUpKaKaoData(
    val userEmail : String,
    val profileImage : String,
    val nickname : String,
    val pushAgree : Boolean,
    val emailAgree : Boolean
)
