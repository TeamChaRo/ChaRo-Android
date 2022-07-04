package com.charo.android.data.model.request.alarm

import com.google.gson.annotations.SerializedName

data class RequestReadPushData(
    @SerializedName("pushId")
    val pushId : Int
    )
