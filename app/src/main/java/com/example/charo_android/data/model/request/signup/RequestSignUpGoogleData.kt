package com.example.charo_android.data.model.request.signup

data class RequestSignUpGoogleData(
    val userEmail : String,
    val profileImage : String,
    val pushAgree : Boolean,
    val emailAgree : Boolean
)
