package com.charo.android.domain.model.signin

data class EmailSignInData(
    val success: Boolean,
    val data: EmailSignIn
) {
    data class EmailSignIn(
        val userEmail: String?,
        val nickname: String?,
        val profileImage: String?,
        val isSocial: Boolean
    )
}

