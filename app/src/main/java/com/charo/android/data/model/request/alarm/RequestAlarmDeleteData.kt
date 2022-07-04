package com.charo.android.data.model.request.alarm

import com.google.gson.annotations.SerializedName

// 확인요망
data class RequestAlarmDeleteData (
    @SerializedName("postId")
    val postId: Int
    )