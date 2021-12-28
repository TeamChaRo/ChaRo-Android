package com.example.charo_android.domain.model.signin

import android.provider.ContactsContract

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

