package com.charo.android.presentation.util

sealed class ResultWrapper<out T>{
    data class Success<out T>(val data : T) : ResultWrapper<T>()
    data class GenericError(val code : Int?, val msg : String?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
