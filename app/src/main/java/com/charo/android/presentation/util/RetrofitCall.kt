package com.charo.android.presentation.util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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
                Timber.d("server connect error")
                Timber.d("server connect $response")
                Timber.d("server connect ${response.message()}")
                Timber.d("server connect ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            Timber.d("Network error:$t")
        }
    })
}