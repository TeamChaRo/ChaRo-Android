package com.charo.android.domain.usecase

import java.lang.Exception

sealed class Result<out T> {
    data class Success<T>(val data:T) : Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}

//val Result<*>.succeeded
//    get() = this is Result.Success && data != null
//
//val <T> Result<T>.data //Success일 경우에만 사용하도록 주의
//    get() = (this as? Result.Success)?.data ?: throw Exception("Result data is null")