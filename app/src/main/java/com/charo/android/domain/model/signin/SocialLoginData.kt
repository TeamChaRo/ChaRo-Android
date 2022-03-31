package com.charo.android.domain.model.signin

data class SocialLoginData(
    val success: Boolean,
    val email: String,
    val nickname: String,
    val profileImage: String,
    val isSocial : Boolean
)
