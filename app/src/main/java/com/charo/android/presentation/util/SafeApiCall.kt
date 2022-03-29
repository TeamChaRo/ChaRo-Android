package com.charo.android.presentation.util

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher : CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T>{
    return withContext(dispatcher){
        try{
            ResultWrapper.Success(apiCall.invoke())
        }catch (throwable : Throwable){
            when(throwable){
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(code)
                }
                else -> {
                    ResultWrapper.GenericError(null)
                }
            }
        }
    }

}