package com.charo.android.data.model.request.alarm

import com.google.gson.annotations.SerializedName

// νμΈμλ§
data class RequestAlarmListData (
    @SerializedName("userEmail")
    val userEmail: String
    )