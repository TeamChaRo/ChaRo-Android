package com.charo.android.presentation.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
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
                    val msg = try {
                        val jsonObject = JSONObject(throwable.response()?.errorBody()?.string())
                        when {
                            jsonObject.has("msg") -> jsonObject.getString("msg")
                            else -> "Something wrong happened"
                        }
                    } catch (e: Exception) {
                        "Something wrong happened"
                    }
                    ResultWrapper.GenericError(code, msg)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }

}