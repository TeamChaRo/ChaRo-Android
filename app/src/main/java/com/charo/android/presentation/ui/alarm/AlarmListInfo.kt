package com.charo.android.presentation.ui.alarm

// 확인요망
data class AlarmListInfo(
    val pushId : Int,
    val pushCode : Int,
    val isRead : Int,
    val token : String,
    val image : String,
    val title : String,
    val body : String,
    val month : String,
    val day : String,

)
