package com.charo.android.presentation.util

import retrofit2.HttpException

fun Throwable.isServerError(): Boolean {
    return this is HttpException && this.code() / 100 == 5
}