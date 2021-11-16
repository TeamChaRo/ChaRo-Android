package com.example.charo_android.domain.model.signin

data class EmailSignInData(
    val userEmail : String ?= "",
    val nickname : String ?= "",
    val profileImage : String ?= "",
    val isSocial : Boolean
)