package com.charo.android.presentation.ui.alarm

// νμΈμλ§
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
