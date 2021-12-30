package com.example.charo_android.presentation.util

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.enqueueUtil(
    onSuccess: (ResponseType) -> Unit,
    onError: ((stateCode: Int) -> Unit)? = null
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response.body() ?: return)
            } else {
                onError?.invoke(response.code())
                Log.d("server connect", "error")
                Log.d("server connect", "$response")
                Log.d("server connect", response.message())
                Log.d("server connect", "${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            Log.d("Network", "error:$t")
        }
    })
}